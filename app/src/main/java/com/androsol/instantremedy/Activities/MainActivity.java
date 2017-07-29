package com.androsol.instantremedy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;

import com.androsol.instantremedy.Adapters.BodyPartAdapter;
import com.androsol.instantremedy.Constants;
import com.androsol.instantremedy.Models.BodyPart;
import com.androsol.instantremedy.Models.Query;
import com.androsol.instantremedy.Models.Symptom;
import com.androsol.instantremedy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    BodyPartAdapter adapter;
    ArrayList<BodyPart> bodyParts;
    DatabaseReference bodyPartRef, symptomRef, queryRef;
    SharedPreferences checkFirstPref = null;
    ArrayList<Symptom> symptoms;
    BodyPart bp;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;
    Intent intent;

    //user help for name and other profile things
    String name, email, photoURL;

    //for signing out exxplicitly from google
    FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;


    //DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        if(mNavigationView != null){
            mNavigationView.setNavigationItemSelectedListener(this);
        }


        intent = getIntent();
        name = intent.getStringExtra(Constants.USER_NAME);
        email = intent.getStringExtra(Constants.USER_EMAIL);
        photoURL = intent.getStringExtra(Constants.USER_PHOTO);


        //for signing out exxplicitly from google
        mFirebaseAuth = FirebaseAuth.getInstance();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();


        bodyPartRef = FirebaseDatabase.getInstance().getReference("bodyparts");
        queryRef = FirebaseDatabase.getInstance().getReference("query");
        symptomRef = bodyPartRef.child("symptoms");
        //symptomRef = FirebaseDatabase.getInstance().getReference("symptoms");
        checkFirstPref= getSharedPreferences("com.androsol.instantremedy",MODE_PRIVATE);

        //dbHelper = new DbHelper(this);
        bodyParts = new ArrayList<BodyPart>();
        recyclerView = (RecyclerView) findViewById(R.id.body_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BodyPartAdapter(bodyParts,this);
        recyclerView.setAdapter(adapter);

    }
//    public boolean openQueryListActivity(){
//        Intent i = new Intent(this, QueryDisplayActivity.class);
//        startActivity(i);
//        return true;
//    }
//    public boolean openAboutActivity(){
//        Intent i = new Intent(this, AboutInstantRemedyActivity.class);
//            startActivity(i);
//        return true;
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_menu, menu);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);

        if(item.getItemId() == R.id.menu_query_display_actvity_option){
            Intent i = new Intent(this, QueryDisplayActivity.class);
            i.putExtra(Constants.USER_EMAIL,email);
            startActivity(i);
            return true;
        }
        if(item.getItemId() == R.id.menu_instant_remedy_about_option){
            Intent i = new Intent(this, AboutInstantRemedyActivity.class);
            startActivity(i);
            return true;
        }
        if(item.getItemId() == R.id.menu_log_out){
            // FirebaseAuth.getInstance().signOut();

            Intent i = new Intent(this, Login3Activity.class);
            startActivity(i);
        }
        if(item.getItemId() == R.id.menu_my_profile){
            Intent i = new Intent(this, MyProfileActivity.class);
            i.putExtra(Constants.USER_NAME,name);
            i.putExtra(Constants.USER_EMAIL,email);
            i.putExtra(Constants.USER_PHOTO,photoURL);
            startActivity(i);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkFirstPref.getBoolean("firstrun", true)) {
            //prepopulated data

            //commenting this
        //    setUpDatabase();
            //additional lines: -
            Snackbar.make(this.findViewById(android.R.id.content),"Setting up the views...",Snackbar.LENGTH_LONG).show();
            checkFirstPref.edit().putBoolean("firstrun",false).commit();

        }
    }
        public void setUpDatabase(){
            Query demoQuery = new Query("Demo","firstQuery, its is a demo","this is optional text","DemoBodyPartId","DemoBodyPartName");
            queryRef.child("Demo").setValue(demoQuery);

            //Symptoms.........................................................................................
            //111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
            symptoms = new ArrayList<Symptom>();
            String id = symptomRef.push().getKey();
            //pehla
            Symptom s = new Symptom(id, "Apathy","Anxiety, Constipation, Depressed Mood, Difficulty sleeping"
            ,"Disease can be DEPRESSION"+
                    "DESCRIPTION: Depression is a painful sadness or down mood that interferes with daily life. " +
                    "Many people feel down for short periods, but depression lasts a long time and may include anxiety, insomnia, and" +
                    " other symptoms. Life events such as the death of a loved one can trigger depression. The illness can run in families," +
                    " but people with no family history also become depressed. Often, there is no clear cause. Depression is a common but serious" +
                    " illness that usually doesn't go away without treatment." +
                            " Counseling and/or antidepressant medication can treat depression in most people",
                    "Treatment Depression treatment may include: [ LONG TERM DISEASE]\n" +
                            "Antidepressant medications, such as fluoxetine (Prozac), paroxetine (Paxil), sertraline (Zoloft), and venlafaxine (Effexor)\n" +
                            "• Counseling\n" +
                            "• Electroconvulsive therapy for severe depression and if antidepressants don't work\n" +
                            "• EAT NUTRITIOUS FOOD.\n" +
                            "• TRY SPENDING TIME WITH PEOPLE WHO MAKE YOU LAUGH\n" +
                            "MORE: http://www.medicinenet.com/depression/article.htm");
            //symptomRef.child(id).setValue(s);
            symptoms.add(s);
            //doosra
            id = symptomRef.push().getKey();
            s = new Symptom(id, "Anxiety", "Pain or discomfort, Dizziness, Giddiness, Irregular heartbeat, vomiting"
            ,"Disease can be PANIC ATTACK\n" +
                    "DESCRIPTION: When someone has a panic attack, that person feels a sudden, intense fear that can't be controlled. People who have panic attacks often feel like they are having a heart attack, losing control, suffocating, or dying. During the panic attack, the person also may have chest pain, nausea, shortness of breath, chills, sweating, dizziness, or a feeling of choking. Doctors don't know for certain what causes panic attacks, but it may have to do with genetics or stress",
                    "Treatment for panic attacks may include:\n" +
                            "• Antidepressant medications, such as fluoxetine (Prozac), paroxetine (Paxil), sertraline (Zoloft), and venlafaxine (Effexor)\n" +
                            "• Mild sedatives, such as alprazolam (Xanax) and clonazepam (Klonopin)\n" +
                            "• Cognitive-behavioral therapy, psychodynamic therapy, or other types of therapy\n" +
                            "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Bleeding","Stiffness or decreased movement, lump or bulge",
                    "Disease can be TRAUMA OR INJURY\n" +
                            "DESCRIPTION: Trauma most often refers to serious bodily injury or wounds. Accidents, falls, blows, burns, or weapons can cause physical trauma. But trauma can also be emotional or psychological injury from an extremely distressing or shocking event, such as an accident, rape, the breakup of a relationship, or a loved one's sudden death. It can occur without physical injury. Acute trauma symptoms are those that happen immediately after injury – such as bruises or bleeding, or shock and denial. Chronic trauma symptoms can occur long after an injury appears to have healed. They can be physical – like pain, headaches, or fatigue – or emotional – like anger, anxiety, and difficulty concentrating. Trauma usually requires medical or psychological treatment"
                    ,"Treatment for physical trauma depends on the nature and severity of the injury. Surgery, medical procedures, medication, and physical or occupational therapy are common treatments. Emotional or psychological trauma may be treated with medication, talk therapy, cognitive behavioral therapy, or other types of psychological therapy.\n" +
                    "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Headache","pain or discomfort, difficulty in concentrating, stiff neck",
                    "Disease can be TENSION HEADACHE\n" +
                            "DESCRIPTION: Tension headaches are the most common type of headache, caused by muscle tension in the neck, face, jaw, or shoulders. Many people have occasional tension headaches, which often start in the middle of the day. For others, tension headaches occur every day. Everyday stress, lack of sleep, anxiety, depression, bad posture, hunger, and tiredness can all trigger a tension headache."
                    ,"Treatment for tension headaches includes: \n" +
                    "\n" +
                    "Pain relievers \n" +
                    "Muscle relaxants \n" +
                    "Medication to help prevent headaches\n" +
                    "\n" +
                    "\n" +
                    "Spend time with your friends\n" +
                    "Try to do your fast activities\n" +
                    "Proper sleep and rest.\n" +
                    "Listen light MUSIC\n" +
                    "Maintain social contacts\n" +
                    "\n" +
                    "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView\n");
            symptoms.add(s);

//            id = symptomRef.push().getKey();
//            s = new Symptom(id,"Fever","Cough Cold, Loss of voice, swollen gland or tonsils, muffed voice",
//                    "Disease can be VIRAL PHARYNGITIS\n" +
//                            "DESCRIPTION: Viral pharyngitis is a sore throat caused by a virus, which usually occurs with a cold or flu. Along with throat pain, you may have other cold symptoms, including runny or stuffy nose, cough, fever, and headache. Symptoms usually get better in a few days with rest and self-care. Antibiotics will not treat a viral sore throat"
//                    ,"TREATMENT: includes SELF CARE\n" +
//                    "• Gargling with warm salt water\n" +
//                    "• Medications to relieve fever and pain, such as acetaminophen (Tylenol) or ibuprofen (Advil, Motrin), or over-the-counter throat sprays\n" +
//                    "• Drinking warm liquids to soothe the throat\n" +
//                    "• Sucking on hard candies or throat lozenges\n" +
//                    "• Rest\n" +
//                    "• Not smoking\n" +
//                    "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView");
//            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id,"Head Scalp",symptoms);
            bodyPartRef.child(id).setValue(bp);

            //............................................................................................................

            symptoms = new ArrayList<Symptom>();
            //222222222222222222222222222222222222222222222222222222222222222222222222222
            id = bodyPartRef.push().getKey();
            s = new Symptom(id,"Dizziness","Fatigue, Irregular heartbeat, Nervousness, Pressure or heaviness, Shortness of breath, Fainting,Pain or discomfort, Feeling faint, Lightheadedness"
                    ,"Disease can be HEART RHYTHM DISORDER\n" +
                    "DESCRIPTION: Heart rhythm disorder – heart arrhythmia -- happens when the electrical impulses that control your heartbeat don't work correctly. Your heart may beat too slowly, too quickly, or have an irregular beat. Many heart rhythm problems are harmless. But some long-lasting heart rhythm disorders can cause a fluttering in the chest, shortness of breath, chest pain, or dizziness. Atrial fibrillation, when the upper chambers of the heart beat fast and out of rhythm, is a common heart rhythm disorder. It can be dangerous over time. Another type of problem, ventricular fibrillation, can cause death within minutes. It causes the lower chambers of the heart to quiver, so the heart can't pump any blood. Ventricular fibrillation is an emergency and needs to be treated with an electrical shock (defibrillator) to keep the heart working."
                    ,"Treatment for heart rhythm disorders varies depending on the type of disorder you have. Some of these treatments include: \n" +
                    "\n" +
                    "Avoid excessive exercise and stress\n" +
                    "Drug: Atropine\n" +
                    "Don’t take overload \n" +
                    "Don’t be panic, try to get calm asap.\n" +
                    "\n" +
                    "\n" +
                    "Medications to stop the arrhythmia \n" +
                    "A pacemaker or implanted cardiac defibrillator (ICD) \n" +
                    "Cardioversion, a shock to reset your heart \n" +
                    "Ablation therapy, which destroys tissue to stop the abnormal heartbeat \n" +
                    "Surgery\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView\n");
            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            s = new Symptom(id, "Heartburn","Pain or discomfort, nighttime wheezing, belching, cough, hoarse voice\n" +
                    "","Disease can be HEARTBURN/GERD\n" + "DESCRIPTION: Heartburn happens when stomach acid backs up into your esophagus, irritating the lining of the esophagus. Usually when you swallow, a muscle around the bottom of the esophagus opens to let food go through and then closes. When it gets weak or doesn't close right, it lets stomach acid flow back into your esophagus. Heartburn causes a burning sensation in your mouth or chest or a sour taste in your mouth. Mild, occasional heartburn usually isn't a problem. Heartburn that happens frequently is called gastroesophageal reflux disorder, or GERD. It is also possible to have GERD without symptoms of discomfort in your chest. GERD without symptoms has been linked to causing ear infections, sinus infection, excessive mucus in the throat. Over time, GERD can wear away at your esophagus and increase your risk for esophageal cancer and other problems."
                    ,"Treatment for heartburn and GERD usually begins with over-the-counter antacids and medications to help reduce stomach acid. You also may want to avoid the foods and drinks that make your symptoms worse. If these treatments don't relieve symptoms, you may need prescription medications, surgery, or other procedures\n" +
                    "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView");
            symptoms.add(s);
//
            id = bodyPartRef.push().getKey();
            s = new Symptom(id, "Breathlessness","Discomfort, heaviness, chest pain, lack of sleep, anxiety, weakness\n",
                    "Disease can be HEART ATTACK\n" +
                    "DESCRIPTION: The heart muscle requires a constant supply of oxygen rich blood to nourish it. The coronary arteries provide the heart with this critical blodd supply.\n" +
                    "A heart attack occurs when the flow of blood to the heart is blocked, most often by a build-up of fat, cholesterol and other substances, which form a plaque in the arteries that feed the heart (coronary arteries). The interrupted blood flow can damage or destroy part of the heart muscle."
                    ,"Rub your Chest, Aspirin,use a defibrilattor, nytroglycerin, avoid heavy food, bypass surgery, angioplasty, and call the ambulance.\n");
            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            s = new Symptom(id, "Headache, Nausea","Vomiting, dizziness, double vision epistaxis, dyspnea, seizure, lethargy\n",
                    "Disease can be HYPERTENSION\n" +
                            "DESCRIPTION:Hypertension (HTN or HT), also known as high blood pressure (HBP), is a long term medical condition in which the blood pressure in the arteries is persistently elevated.[8] High blood pressure usually does not cause symptoms.[1] Long term high blood pressure, however, is a major risk factor for coronary artery disease, stroke, heart failure, peripheral vascular disease, vision loss, and chronic kidney disease."
                    ,"Drink liquid and take some sweet item, do regular exercises, reducing alcohol consumption, lowering salt intake, lowering caffeine intake, ACE inhibitors: Calcium CHannel Blockers, Dueretics(Thiazide) , beta blockers.\n");
            symptoms.add(s);
//

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Heart",symptoms);
            bodyPartRef.child(id).setValue(bp);

            //////////////////////////////////////////////////////////////////////////
            symptoms = new ArrayList<Symptom>();
            //33333333333333333333333333333333333333333333333333333

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Warm to touch","pain, chills, fever, lumo or bulge, enlarge or swollen glands",
                    "Disease can be Lung ABSCESS\n" +
                            "DESCRIPTION: A Lung abscess is an inflamed pocket of pus that develops below the Lung surface. It\n" +
                            "causes swelling, pain, and tenderness on the skin. Also called boils, Lung abscesses often show up in\n" +
                            "places where you sweat or there is friction, such as the armpits, groin, buttocks, face, or neck.\n" +
                            "Staphylococcus aureus and streptococci infections are the main cause. These bacteria can enter the\n" +
                            "Lung through splinters, scrapes, and inflamed hair follicles. Some Lung abscesses rupture and drain on\n" +
                            "their own and some need to be treated by a doctor. Bacteria from Lung abscesses are dangerous if\n" +
                            "they spread to the bloodstream, lymph nodes, or deeper tissue.",
                    "Treatment may include: \n" +
                            "AVOID SMOKING\n" +
                            "TAKE DEEP BREATHS\n" +
                            "Chest Physiotherapy\n" +
                            "Oxygen Therapy\n" +
                            "\n" +
                            "\n" +
                            "Warm compresses or soaks \n" +
                            "Incision and drainage by a doctor \n" +
                            "Antibiotics\n");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Rapid breathing","pain or discomfort, cough, difficulty sleeping, nighttime wheezing",
                    "Disease can be ASTHMA\n" +
                            "DESCRIPTION: Asthma is a lung condition that causes airways to swell and become inflamed. Asthma\n" +
                            "makes it hard to breathe. Some people may have minor symptoms, but others may have severe\n" +
                            "symptoms that interfere with daily life. Having severe asthma may put you at risk for a life-threatening\n" +
                            "asthma attack. Symptoms of an asthma attack include wheezing, chest tightness, coughing, and\n" +
                            "shortness of breath. Doctors don't know exactly what causes asthma, but genetic and environmental\n" +
                            "factors may play a role. Triggers such as smoking, air pollution, allergies, exercise, stress, GERD, and\n" +
                            "colds can bring on asthma attacks. Asthma is a lifelong condition that can be managed but not cured.\n" +
                            "Avoiding asthma triggers and taking medication help most people avoid attacks and keep symptoms\n" +
                            "under control.",
                    "Treatment for asthma includes an asthma action plan that has in writing when to take certain\n" +
                            "medications based on your symptoms. Medications include both those for long-term control and for\n" +
                            "quick relief:\n" +
                            "Intake fresh air"+
                            "Cover your mouth and nose"+
                            "Keep away from dust"+
                            " Long-term control medications include inhaled corticosteroids, leukotriene modifiers, long-acting beta\n" +
                            "agonists, combination inhalers, omalizumab (Xolair).\n" +
                            " Quick-relief or rescue medications include short-acting bronchodilators and oral corticosteroids.\n" +
                            " Allergy treatment, if your asthma is triggered by allergies.");

            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Drainage or pus","pain, cough, sweeling, headache, bloody or red coloured stools",
                    "Disease can be ALLERGIC REACTION.\n" +
                            "DESCRIPTION: An allergic reaction occurs when your immune system overreacts to an allergen, or\n" +
                            "normally harmless substance. Animal dander, pollen, dust, medications, insect bites, and foods such\n" +
                            "as peanuts and shellfish are common allergens. Most allergic reactions are mild, but others can be\n" +
                            "severe and life threatening. Severe reactions are called anaphylaxis. Anaphylaxis is a medical\n" +
                            "emergency because a person can die without fast treatment. Emergency care includes an injection\n" +
                            "with epinephrine, an adrenalin medication",
                    "Treatment for anaphylaxis may include:\n" +
                            ".Avoid Allergers\n"+
                            "• Epinephrine injection\n" +
                            "• Oxygen\n" +
                            "• Intravenous fluid\n" +
                            "• Antihistamines\n" +
                            "• Corticosteroids\n" +
                            "• Avoiding triggers in the future\n" +
                            "• Immunotherapy to prevent future reactions");
            symptoms.add(s);


            id = symptomRef.push().getKey();
            s = new Symptom(id,"Cough and Fever"," CHEST PAIN, SHAKING & CHATTERING TEETH, FAST HEARTBEAT, VOMITING, NAURSEA\n",
                    "DISEASE CAN BE PNEUMONIA\n" +
                            "DESCRIPTION:\n" +
                            "Pneumonia is an inflammatory condition of the lung affecting primarily the microscopic air sacs known as alveoli Typical signs and symptoms include a varying severity and combination of productive or dry cough, chest pain, fever, and trouble breathing, depending on the underlying cause. Pneumonia is usually caused by infection with viruses or bacteria and less commonly by other microorganisms, certain medications and conditions such as autoimmune diseases. Risk factors include other lung diseases such as cystic fibrosis, COPD, and asthma, diabetes, heart failure, a history of smoking, a poor ability to cough such as following a stroke, or a weak immune system\n",
                    "TAKE ANTIBIOTIC\n" +
                            "AVOID SMOKING\n" +
                            "TAKE REST & TRY TO SLEEP\n" +
                            "MAINTAIN HYGIENE\n");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Coughing with blood","SHORTENING OF BREATH, WEIGHT LOSS, SEVERE CHEST PAIN, RESPIRATORY PROBLEMS\n",
                    "DISEASE CAN BE LUNG CANCER/ CARCINOMA OF LUNG\n" +
                            "DESCRIPTION: Lung cancer, also known as lung carcinoma,[7] is a malignant lung tumor characterized by uncontrolled cell growth in tissuesof the lung.[10] This growth can spread beyond the lung by the process of metastasis into nearby tissue or other parts of the body.[11] Most cancers that start in the lung, known as primary lung cancers, are carcinomas.[12] The two main types are small-cell lung carcinoma (SCLC) and non-small-cell lung carcinoma (NSCLC)\n",
                    "TRY TO GET FRESH OXYGEN\n" +
                            "CHEMOTHERAPY\n" +
                            "RADIOTHERAPY\n" +
                            "AVOID SMOKING\n");
            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Chest",symptoms);
            bodyPartRef.child(id).setValue(bp);
            ////////////////////////////////////////////////////////////////////////////////////////////////
            symptoms = new ArrayList<Symptom>();
            ///444444444444444444444444444444444444444444444444444444444444444444


            id = symptomRef.push().getKey();
            s = new Symptom(id,"Muscle cramps or spasms","pain, bloating or fullness, more passing of gas, Vomiting with blood, Nausea",
                    "Disease can be\n" +
                            "DESCRIPTION: Everyone has gas in his/her digestive tract and eliminates it by belching or passing\n" +
                            "gas. When you cannot pass the gas, it may cause brief but intense abdominal pain. Gas comes from\n" +
                            "swallowing air when you eat or drink and from the breakdown of foods in the large intestine. Half of\n" +
                            "the gas passed through the rectum comes from swallowed air. Some foods and food additives are\n" +
                            "harder to digest and may cause excess gas in the large intestine. These include high-fiber foods such\n" +
                            "as beans, whole grains, and certain fruits and vegetables",
                    "Treatment ; Avoid foods that give you gas\n" +
                            "Milk of Magnesia"+
                            "Lemon, Banana, Almonds will give instant relief"+
                            "Antacids"+
                            "Avoid pickles, spicy food"+
                            "Boil mint leaves in water and have a glass of it after every meal"+
                            "Eat slowly\n" +
                            "Avoid chewing gum\n" +
                            "Quit smoking\n" +
                            "Take supplements to help digest certain foods\n" +
                            "Treat any underlying medical problem");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Distended stomach","pain, bad breath, decreased appetite",
                    "Disease can be CONSTIPATION\n" +
                            "DESCRIPTION: Constipation is having less than three bowel movements a week. You also may have\n" +
                            "hard, dry stools that are difficult to pass. Everyone has constipation at one time or another. It's usually\n" +
                            "nothing to worry about. The foods you eat, how often you exercise, and other factors affect how often\n" +
                            "you have bowel movements. Many people expect to have one bowel movement a day, but most\n" +
                            "people aren't that regular. A normal range is anywhere from three to 21 bowel movements a week.\n" +
                            "However, prolonged constipation can lead to hemorrhoids. If you have constipation, understanding\n" +
                            "what causes it can help you avoid it",
                    "Treatment :Most cases of constipation can be treated with lifestyle changes. In some cases, the\n" +
                            "doctor may recommend using laxatives for a short time\n" +
                            "EAT BANANA\n" +
                            "DRINK FLUIDS\n" +
                            "‘EAT FIBROUS DIET\n" +
                            "OSMOTIC AGENT\n" +
                            "STOOL SOFTENER\n" +
                            "MILK OF MAGNESIA\n" +
                            "EXERCISE\n");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Foul smelling stools","pain, decreased appetite, bloody vomit",
                    "Disease can be gastroenteritis\n" +
                            "DESCRIPTION: Gastroenteritis is irritation of the stomach and intestines. Viruses, bacteria, parasites,\n" +
                            "or food-borne illness can cause it. Gastroenteritis symptoms include abdominal pain and cramping,\n" +
                            "nausea, diarrhea, and vomiting. While sometimes called the \"stomach flu,\" the flu virus does not\n" +
                            "cause gastroenteritis. The illness usually spreads easily from contact with a sick person. Eating or\n" +
                            "drinking contaminated food or beverages also will spread the illness. Most people recover in a few\n" +
                            "days by drinking plenty of fluids and resting. Antibiotics treat gastroenteritis caused by bacteria or\n" +
                            "some parasites. Infants, older adults, and people with chronic illnesses are at higher risk for\n" +
                            "dehydration from gastroenteritis",
                    "Treatment for gastroenteritis may include:\n" +
                            "• Drinking clear fluids. To replace the electrolytes lost from vomiting and diarrhea, try an electrolyte\n" +
                            "replacement drink. Or add a pinch of salt to a glass of water.\n" +
                            "• Anti-nausea and anti-diarrhea medications, if recommended by your doctor\n" +
                            "• Antibiotics for bacterial infections\n" +
                            "• Acetaminophen (Tylenol) as needed to ease muscle aches and lower fever. Avoid anti-inflammatory\n" +
                            "medications such as ibuprofen (Advil, Motrin) or naproxen (Aleve), which can irritate the stomach");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Feeling hard lump around anus","BRIGHT RED BLOOD AFTER A SMALL MOVEMENT, PAIN WHILE DEFECATING\n" +
                    "DISEASE  AN BE PILES\n",
                   "Hemorrhoids, also called piles, are vascular structures in the anal canal.[7][8] In their normal state, they are cushions that help with stool control.[2] They become a disease when swollen or inflamed; the unqualified term \"hemorrhoid\" is often used to refer to the disease.[8] The signs and symptoms of hemorrhoids depend on the type present.[4] Internal hemorrhoids are usually present with painless, bright red rectal bleeding when defecating.[3][4] External hemorrhoids often result in pain and swelling in the area of the anus. If bleeding occurs it is usually darker.[4] Symptoms frequently get better after a few days.[3] A skin tag may remain after the healing of an external haemorrhoid\n",
                    "PSYLLIUM HUSK\n" +
                            "USE ALEOVERA, APPLE CIDER VINEGAR\n" +
                            "AVOID SPICY FOOD & PICKLES EAT FRUIT VEGETABLES\n" +
                            "USE OINTMENTS, PADS\n" +
                            "PAINKILLER : ACETORMINOPHAN\n");
            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Stomach",symptoms);
            bodyPartRef.child(id).setValue(bp);
            ///////////////////////////////////////////////////////////////////////////////////
            symptoms = new ArrayList<Symptom>();
            //5555555555555555555555555555555555555555555555555555555555555
            id = symptomRef.push().getKey();
            s = new Symptom(id,"Redness in eye","Bleeding in eye, red bloodshot eyes","Disease can be SUBCONJUNCTIVALHEMORRHAGE\n" +
                    "DESCRIPTION: A subconjunctival hemorrhage is a bright red patch in the white of the eye. It occurs\n" +
                    "when blood vessels break and bleed just below the surface of the eye. Sneezing, coughing, straining,\n" +
                    "high blood pressure, certain medications, eye injury, bleeding disorders, and even rubbing your eye\n" +
                    "can cause blood vessels to rupture. Often, there is no cause. The condition causes no pain or\n" +
                    "discharge, and you may first notice it when looking in a mirror. Most of the time, the red patch goes\n" +
                    "away within two weeks and requires no treatment.\n" +
                    "The red patch may appear to get bigger in the first 24 hours. Then it will slowly get smaller as the\n" +
                    "blood is absorbed by the body. Similar to a bruise, a subconjunctival hemorrhage changes colors --\n" +
                    "red to orange to yellow -- as it heals. The hemorrhage should disappear completely within two weeks.",
                    " WASH YPUR EYES WITH COLD WATER\n" +
                            "USE LUBRICANT EYEDROPS\n" +
                            "DON’T RUB YOUR EYES \n" +
                            "USE SUNGLASSES\n" +
                            "The red patch may appear to get bigger in the first 24 hours. Then it will slowly get smaller as the blood is absorbed by the body. Similar to a bruise, a subconjunctival hemorrhage changes colors -- red to orange to yellow -- as it heals. The hemorrhage should disappear completely within two weeks\n");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Blindness","pain, weakness, confusion, fatigue",
                    "Disease can be MULTIPLE SCLEROSIS\n" +
                            "DESCRIPTION: MS is a disease that affects the nerves in the brain and spinal cord and can cause\n" +
                            "difficulties with balance, speech, movement, and vision. MS seems be an immune disorder -- for\n" +
                            "some reason, the body's own immune system attacks healthy nerves. MS is unpredictable. Most\n" +
                            "people have mild cases but it can be debilitating. Symptoms and severity depend on which nerves are\n" +
                            "affected. MS is more common in women. There is no cure but treatment can help. Remember that MS\n" +
                            "is rare and that many less serious conditions can cause similar symptoms",
                    "Treatments for MS include:\n" +
                            "• Medications such as interferons and drugs to suppress the immune system\n" +
                            "• Corticosteroids\n" +
                            "• Medications to help with symptoms such as mood issues, fatigue, and muscle spasms\n" +
                            "• Physical therapy");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Blurred vision, Bright flashing dots","Flickering light in vision, headache, vomiting, sensitive to light and noise",
                    "DESCRIPTION: Migraines are a common type of headache that can cause severe pain. They can\n" +
                            "last for a few hours or a few days and may cause throbbing, sensitivity to sound or light, nausea, or\n" +
                            "vomiting. Migraines are caused by abnormal brain activity that is triggered by certain foods, stress, or\n" +
                            "other factors. Some people have an aura before a migraine, which can have symptoms that include\n" +
                            "temporary vision loss, seeing stars or flashes, or a tingling in an arm or leg. There is no cure for\n" +
                            "migraines, but medications can help reduce pain or stop migraines from occurring",
                    "Some migraines can be treated at home with over-the-counter pain relievers.\n" +
                            ".Avoid bright light"+
                            ".Dont skip meals"+
                            ".Avoid tea, coffee, chocolates"+
                            ".Drink lots of water"+
                            "Dont provoke trigger point"+
                            ".Severe migraines are usually treated with prescription medicines called triptans, anti-nausea\n" +
                            "medicine, or sedatives. Pain-relieving medications usually work best when taken at the first sign of a\n" +
                            "migraine.\n" +
                            ".Frequent migraines can sometimes be prevented by taking medications such as beta-blockers,\n" +
                            "antidepressants, antiseizure drugs, or by getting a Botox injection in the muscles of the forehead and\n" +
                            "neck.");
            symptoms.add(s);
            id = symptomRef.push().getKey();
            s = new Symptom(id,"Puffy Eyelids","feeling faint, restless, fatigue",
                    "Disease can be SLEEP DEPRIVATION\n" +
                            "DESCRIPTION: Sleep deprivation occurs when the lack of restful sleep is severe enough to\n" +
                            "compromise basic body functions and thinking. Different people need different amounts of sleep. But\n" +
                            "on average, adults need 7 to 9 hours each night to avoid sleep deprivation. People become sleep\n" +
                            "deprived for many reasons. Some have sleep apnea, restless leg syndrome, or insomnia. Others\n" +
                            "work night shifts, and others simply try to get by on little sleep. Whatever the reason, sleep\n" +
                            "deprivation can cause daytime sleepiness, fatigue, lack of concentration, irritability, erection\n" +
                            "problems, and depression. It also is linked to weight gain and may increase the risk of heart disease",
                    "TREATMENT:\n" +
                            "Good sleep habits can help you avoid sleep deprivation. See tips in the Self-Care section below. If\n" +
                            "you have a specific sleep problem, the doctor may recommend:\n" +
                            ".Massage"+
                            ".Listen calm music"+
                            ".Meditation, yoga"+
                            ".Medications to ease your sleep problem -- certain drugs treat insomnia, others treat restless leg\n" +
                            "syndrome, and others treat narcolepsy\n" +
                            ".For sleep apnea, a CPAP machine to deliver a constant flow of air into your nostrils while you sleep\n" +
                            ".Surgery to repair a deviated septum and other structural problems that interfere with breathing while\n" +
                            "you sleep");
            symptoms.add(s);
            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Eye",symptoms);
            bodyPartRef.child(id).setValue(bp);
            ///////////////////////////////////////////////////////////////////////////////////////
            symptoms = new ArrayList<Symptom>();
            //6666666666666666666666666666666666666666666666666666666666666666
            id = symptomRef.push().getKey();
            s = new Symptom(id,"Difficulty in walking","pain, visible deformity, stiffness, broken bone",
                    "Disease can be BROKEN LOW BACK VERTEBRA\n" +
                            "DESCRIPTION: A broken low back occurs when one of the vertebrae in the spine is broken. It is most\n" +
                            "often caused by a severe injury to the spine, such as a car accident, sports injury, or fall. But it also\n" +
                            "can happen if you have a disease such as osteoporosis or cancer. The lower spine is a common spot\n" +
                            "for fractures. A broken back can cause pain in the lower back, upper back, neck, or hip. It also can\n" +
                            "cause weakness or numbness and tingling",
                    "Treatment for a broken back may include:\n" +
                            "• A back brace\n" +
                            "• Pain medications\n" +
                            "• Physical therapy\n" +
                            "• Surgery");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Curved Spine","pain, shortness of breath",
                    "Disease can be SCOLIOSIS\n" +
                            "DESCRIPTION: Scoliosis occurs when the spine curves sideways more than it should. It most often\n" +
                            "happens around puberty and affects more girls than boys. Doctors don't know what causes most\n" +
                            "cases of scoliosis. In some cases, children are born with scoliosis, or a disease such as spina bifida\n" +
                            "or cerebral palsy causes it. Scoliosis can make one shoulder or hip look higher than the other. It can\n" +
                            "also cause backache or fatigue. Most cases are mild, and many times no treatment is needed. Some\n" +
                            "children may need a brace or, in severe cases, surgery.",
                    "Treatment for scoliosis may include:\n" +
                            "• A back brace\n" +
                            "• Pain Medications\n" +
                            "• Physical Therapy\n" +
                            "• Surgery");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Bruising or discoloration","tenderness to touch",
                    "Disease can be BRUISE or CONTUSION\n" +
                            "DESCRIPTION: Bruises, or contusions, occur when an area of the body is hit or injured and small\n" +
                            "blood vessels are broken. The damaged small blood vessels leak under the skin, causing a tender,\n" +
                            "reddish purple area. This is a bruise\n" +
                            "A bruise takes about 24 to 48 hours to fully develop. Then, as it heals, the bruise goes through a\n" +
                            "number of color changes depending on the depth and severity of the bruise. Bruises usually take\n" +
                            "about 2 weeks to disappear.","NO TREATMENT AS SUCH.");
            symptoms.add(s);


            id = symptomRef.push().getKey();
            s = new Symptom(id,"Lump or bulge","chills, fever, pain, skin open sore",
                    "Disease can be ABSCESS\n" +
                            "DESCRIPTION: A skin abscess is an inflamed pocket of pus that develops below the skin surface. It\n" +
                            "causes swelling, pain, and tenderness on the skin. Also called boils, skin abscesses often show up in\n" +
                            "places where you sweat or there is friction, such as the armpits, groin, buttocks, face, or neck.\n" +
                            "Staphylococcus aureus and streptococci infections are the main cause. These bacteria can enter the\n" +
                            "skin through splinters, scrapes, and inflamed hair follicles. Some skin abscesses rupture and drain on\n" +
                            "their own and some need to be treated by a doctor. Bacteria from skin abscesses are dangerous if\n" +
                            "they spread to the bloodstream, lymph nodes, or deeper tissue",
                    "Treatment may include:\n" +
                            "• Warm compresses or soaks\n" +
                            "• Incision and drainage by a doctor\n" +
                            "• Antibiotics");
            symptoms.add(s);
            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Back",symptoms);
            bodyPartRef.child(id).setValue(bp);
            ///////////////////////////////////////////////////////////////////////
            symptoms = new ArrayList<Symptom>();
            //7777777777777777777777777777777777777777777777777777
            id = symptomRef.push().getKey();
            s = new Symptom(id,"Ear ache","pain, hearing loss, itching, drainage",
                    "Disease can be EAR CANAL IMFECTION\n" +
                            "DESCRIPTION: The ear canal is a place where bacteria can easily grow, especially if water is in the\n" +
                            "ear canal. Children and teenagers who spend a lot of time at the pool in the summer are especially\n" +
                            "prone to infection. But anyone can get it. The most common cause is bacteria called pseudomonas.\n" +
                            "Other types of bacteria and fungi can also cause it. An acute infection lasts less than four weeks.\n" +
                            "Chronic infections may last for more than four weeks or occur four or more times in a year. Ear canal\n" +
                            "infections usually clear up when treated with antibacterial drops or antibiotics. But people with\n" +
                            "diabetes or a weakened immune system are at risk for a more severe infection called malignant otitis\n" +
                            "externa. Malignant otitis externa can spread to other tissue, including bone at the base of the skull,\n" +
                            "and it can be life-threatening.",
                    "Treatment may include:\n" +
                            "• Using antibacterial eardrops mixed with a steroid to reduce swelling for 10 days to 14 days\n" +
                            "• Taking antibiotics if you also have an inner ear infection or if the infection has spread to other tissue\n" +
                            "• Taking over-the-counter pain medication such as acetaminophen or ibuprofen\n" +
                            "• Using a warm towel to help relieve pain");
            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Ringing in ears","double vision, decreased urination, dizziness, dry mouth",
                    "Disease can be ASPIRIN POISONING\n" +
                            "DESCRIPTION: Aspirin poisoning can come on suddenly after taking a high dose of aspirin or\n" +
                            "gradually after taking lower doses for several weeks. You may have nausea, vomiting, ringing in your\n" +
                            "ears, drowsiness, confusion, and rapid breathing. Aspirin poisoning is usually a medical emergency\n" +
                            "because it can cause unconsciousness or death. It's a common cause of poisoning in children but\n" +
                            "less so because of child-proof packaging and public education. Intentional aspirin poisoning, such as\n" +
                            "in an attempted suicide, causes most sudden aspirin poisoning. You can develop gradual poisoning\n" +
                            "by unknowingly taking a combination of medications that contain aspirin. Activated charcoal, and in\n" +
                            "severe cases, hemodialysis, are used to treat it. With proper treatment, most people survive",
                    "Treatment for aspirin poisoning may include:\n" +
                            "• Stopping aspirin use immediately\n" +
                            "• Gastric lavage (stomach pumping), depending on when the aspirin was taken\n" +
                            "• Activated charcoal to absorb salicylate from the stomach -- possibly given with a laxative to move it\n" +
                            "through the gastrointestinal system\n" +
                            "• IV fluids for dehydration\n" +
                            "• IV sodium bicarbonate (sometimes with potassium), to help move salicylate to the urine\n" +
                            "• Ventilator, if the person is agitated, in a coma, or having trouble breathing\n" +
                            "• Treatment for complications such as seizures or convulsions\n" +
                            "• Dialysis, to eliminate aspirin from the body\n" +
                            "• Psychiatric therapy, in case of an intentional overdose\n" +
                            "• Hospitalization for further monitoring and care, especially if the person is an infant, elderly, has longterm\n" +
                            "aspirin poisoning, or has taken a sustained-release medication with aspirin");

            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Swelling","pain, lump","Disease can be AURICULAR HEMATOMA\n" +
                    "DESCRIPTION: An auricular hematoma is a painful swelling on the outer ear. After a blow to the ear,\n" +
                    "blood can well up between the stiff cartilage and the skin, causing it to swell. Athletes who play\n" +
                    "contact sports -- such as wresters and rugby players -- are at higher risk. Some people have auricular\n" +
                    "hematomas after getting a piercing in the ear cartilage. Treatment is effective and important.\n" +
                    "Untreated, your ear might become infected or permanently deformed",
                    "TREATMENT:\n" +
                            "If you have an auricular hematoma, your doctor may need to drain the excess blood -- sometimes two\n" +
                            "to three times if the swelling returns. You also may need:\n" +
                            "• Bandages that compress the area to prevent swelling\n" +
                            "• Antibiotics to prevent infection\n" +
                            "• Daily checkups for several days\n" +
                            "• Rest for at least 24 hours");
            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Ear",symptoms);
            bodyPartRef.child(id).setValue(bp);
            /////////////////////////////////////////////////////////////
            symptoms  = new ArrayList<Symptom>();

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Redness, Pain and Tenderness","SWELLING, SHINY SURFACE,\n" +
                    "FEVER, INFLAMMATION, PUS, DRAINAGE\n",
                   "DISEASE CAN BE\n" +
                           "CELLULITIS\n" +
                           "DESCRIPTION: \n" +
                           "Cellulitis is a bacterial infection involving the inner layers of the skin. It specifically affects the dermis and subcutaneous fat. Signs and symptoms include an area of redness which increases in size over a few days. The borders of the area of redness are generally not sharp and the skin may be swollen. While the redness often turns white when pressure is applied, this is not always the case. The area of infection is usually painful.[1] Lymphatic vessels may occasionally be involved,[1][3] and the person may have a fever and feel tired.\n",
                    "ANTIBIOTIC: AMOXOCYLLIN\n" +
                            "AVOID TOUCHING AFFECTED PART\n" +
                            "EXCISION\n" +
                            "PROPER DRESSING\n" +
                            "PROPER HYGIENE\n");

            symptoms.add(s);

            id = symptomRef.push().getKey();
            s = new Symptom(id,"Skin rashes (usually on hands or feet palm)","MOIST PATCHESIN THE GENITALS OR SKIN FOLDS\n" +
                    "FEVER GENERALLY ILL FEELING\n" +
                    "LOSS OF APETITE \n" +
                    "JOINT OR MUSCLE PAIN\n" +
                    "MUCOUS PATCHES IN OR AROUND MOUTH & VAGINA/PENIS\n",
                    "Disease can be Syphils\n" +
                            "DESCRIPTION: Syphilis is a sexually transmitted infection caused by the bacterium Treponema pallidum subspecies pallidum.[2] The signs and symptoms of syphilis vary depending in which of the four stages it presents (primary, secondary, latent, and tertiary). The primary stage classically presents with a single chancre (a firm, painless, non-itchy skin ulceration) but there may be multiple sores. In secondary syphilis a diffuse rash occurs, which frequently involves the palms of the hands and soles of the feet. There may also be sores in the mouth or vagina. In latent syphilis, which can last for years, there are few or no symptoms.[1] In tertiary syphilis there are gummas (soft non-cancerous growths), neurological, or heart symptoms.[5] Syphilis has been known as \"the great imitator\" as it may cause symptoms similar to many other diseases\n"
                    ,"ANTIBIOTIC: PENNICULINS\n" +
                    "(IF YOU ARE SEXUALLY ACTIVE, PRACTICE SAFE SEX AND USE CONDOMS)\n" +
                    "TETRACYCLINS (IT IS DANGEROUS TO UNBORN BABY)\n" +
                    "PENICILLIN IS AN OPTION IN CASE OF PREGNANCY\n");
            symptoms.add(s);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Skin",symptoms);
            bodyPartRef.child(id).setValue(bp);

//PRE POPULATION DONE.........................................................

            Snackbar.make(this.findViewById(android.R.id.content),"Setting up the views...",Snackbar.LENGTH_LONG).show();
            checkFirstPref.edit().putBoolean("firstrun",false).commit();
        }

    @Override
    protected void onStart() {
        super.onStart();
        setUpViews();
    }

    public void setUpViews(){

        bodyPartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bodyParts.clear();
                for(DataSnapshot bodypartSnapshot : dataSnapshot.getChildren()){
                    BodyPart bp = bodypartSnapshot.getValue(BodyPart.class);
                    bodyParts.add(bp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        String query = "SELECT * FROM "+dbHelper.BODY_PARTS_TABLE + ";";
//        Cursor c = db.rawQuery(query,null);
//        bodyParts.clear();
//        c.moveToFirst();
//        while(!c.isAfterLast()){
//            int id = c.getInt(c.getColumnIndex(dbHelper.BODY_PARTS_COLUMN_ID));
//            String name = c.getString(c.getColumnIndex(dbHelper.BODY_PARTS_COLUMN_NAME));
//            BodyPart bp = new BodyPart(id, name);
//            bodyParts.add(bp);
//            c.moveToNext();
//        }
//        if(bodyParts.size() == 0){
//            Log.d("DHRUVNODATA","no data");
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//        }else{
//
//            Log.d("DHRUV","data hai");
//        }
//        Toast.makeText(this, bodyParts.get(0).getName() + "  " + bodyParts.get(bodyParts.size()-1).getName() ,Toast.LENGTH_SHORT);
//
//        db.close();
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
      //  Log.d(TAG, "Connection Failed");

    }


}

