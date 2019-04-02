package edu.upd.engg.cheintel.labsafe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class WhereIsYourEmergency extends AppCompatActivity {
    OWLOntology o;
    OWLDataFactory df;
    HashMap<String, OWLIndividual> chemicalMap;
    HashMap<String, OWLClass> emergencyMap;
    HashMap<String, Integer> flammabilityMap;
    HashMap<String, Integer> healthMap;
    HashMap<String, Integer> instabilityMap;
    HashMap<String, Integer> emergencyChemicalsMap;
    HashMap<String, Integer> eyeMap;
    HashMap<String, Integer> fireMap;
    HashMap<String, Integer> inhalationMap;
    HashMap<String, Integer> skinMap;
    HashMap<String, Integer> spillMap;
    HashMap<String, Integer> ingestionMap;
    ImageView imageView1;
    ImageView loading;
    ImageView legend;
    ImageView abLogo1;
    ImageView abLogo2;
    ImageView abLogo3;
    View view2;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView highLevel;
    TextView lowLevel;
    EditText editText;
    EditText editTextChemical;
    ListView listView;
    List<String> current;
    String typed;
    String selected;
    Integer state;
    MyThread t1;
    MyThread2 t2;

    @Override
    public void onBackPressed() {
        if (state == 0) {
            legend.setVisibility(View.GONE);
            highLevel.setVisibility(View.GONE);
            lowLevel.setVisibility(View.GONE);
            editText.getText().clear();
            editTextChemical.getText().clear();
            super.onBackPressed();
        }
        if ((state == 1) || (state == 2) || (state == 4)) {
            legend.setVisibility(View.GONE);
            highLevel.setVisibility(View.GONE);
            lowLevel.setVisibility(View.GONE);
            state = 0;
            editText.getText().clear();
            editTextChemical.getText().clear();
            changeView();
        }
        if ((state == 3)) {
            legend.setVisibility(View.GONE);
            highLevel.setVisibility(View.GONE);
            lowLevel.setVisibility(View.GONE);
            state = 1;
            editText.getText().clear();
            editTextChemical.getText().clear();
            changeView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_is_your_emergency);
        view2 = findViewById(R.id.view2);
        imageView1 = findViewById(R.id.imageView1);
        abLogo1 = findViewById(R.id.abLogo1);
        abLogo2 = findViewById(R.id.abLogo2);
        abLogo3 = findViewById(R.id.abLogo3);
        legend = findViewById(R.id.legend);
        loading = findViewById(R.id.loading);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        highLevel = findViewById(R.id.highLevel);
        lowLevel = findViewById(R.id.lowLevel);
        editText = findViewById(R.id.editText);
        editTextChemical = findViewById(R.id.editTextChemical);
        listView = findViewById(R.id.listView);
        selected = "";
        state = 0;
        changeView();
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        textView1.setTypeface(type);
        textView2.setTypeface(type);
        textView3.setTypeface(type);
        textView4.setTypeface(type);
        textView5.setTypeface(type);
        textView6.setTypeface(type);
        highLevel.setTypeface(type);
        lowLevel.setTypeface(type);
        chemicalMap = new HashMap<>();
        emergencyMap = new HashMap<>();
        flammabilityMap = new HashMap<>();
        healthMap = new HashMap<>();
        instabilityMap = new HashMap<>();
        eyeMap = new HashMap<>();
        fireMap = new HashMap<>();
        inhalationMap = new HashMap<>();
        skinMap = new HashMap<>();
        spillMap = new HashMap<>();
        ingestionMap = new HashMap<>();
        try {
            InputStream y = this.getAssets().open("OntologySplit1_v1.0.9.owl");
            t1 = new MyThread(y);
            t1.start();
            InputStream z = this.getAssets().open("OntologySplit2_v1.0.1.owl");
            t2 = new MyThread2(z);
            t2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        button1.setOnClickListener(view -> {
            while (t1.isAlive()) {
                state = -1;
            }
            state = 1;
            changeView();
        });
        button2.setOnClickListener(view -> {
            while (t2.isAlive()) {
                state = -1;
            }
            state = 2;
            changeView();
        });
        button4.setOnClickListener(view -> {
            state = 4;
            changeView();
        });
    }


    class MyThread extends Thread {
        InputStream y;
        @Override
        public void run() {
            OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
            try {
                o = oom.loadOntologyFromOntologyDocument(y);
            } catch (Exception e) {
                e.printStackTrace();
            }
            df = oom.getOWLDataFactory();
            String base = "http://www.semanticweb.org/thomasm.lutao/azescobar/ontologies/2017/9/MSDSOntologySplit1-nopropsvers";
            PrefixManager pm = new DefaultPrefixManager(base);
            Set<OWLClass> classes = o.getClassesInSignature(false);
            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
            OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
            OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
            reasoner.precomputeInferences();
            OWLClass emergencies = null;
            for (OWLClass owlClass : classes) {
                String z = owlClass.getIRI().toString();
                z = z.substring(z.lastIndexOf("#") + 1);
                if (z.equals("EmergencyProcedures")) {
                    emergencies = owlClass;
                    break;
                }
            }
            if (emergencies != null) {
                for (OWLClass subClass : reasoner.getSubClasses(emergencies, true).getFlattened()) {
                    String label = subClass.getIRI().getFragment();
                    for (OWLAnnotation annotation : subClass.getAnnotations(o, df.getRDFSLabel())) {
                        if (annotation.getValue() instanceof OWLLiteral) {
                            OWLLiteral val = (OWLLiteral) annotation.getValue();
                            label = val.getLiteral();
                        }
                    }
                    emergencyMap.put(label, subClass);
                }
            }
            OWLClass emergenciesx = null;
            for (OWLClass owlClass : classes) {
                String z = owlClass.getIRI().toString();
                z = z.substring(z.lastIndexOf("#") + 1);
                if (z.equals("NFPAHazardIntensity")) {
                    emergenciesx = owlClass;
                    break;
                }
            }
            if (emergenciesx != null) {
                for (OWLClass subClass : reasoner.getSubClasses(emergenciesx, true).getFlattened()) {
                    String label = subClass.getIRI().getFragment();
                    emergencyMap.put(label, subClass);
                }
            }
            OWLClass emergencyChemicals = null;
            for (OWLClass owlClass : classes) {
                String x = owlClass.getIRI().toString();
                x = x.substring(x.lastIndexOf("#") + 1);
                if (x.equals("ProductName")) {
                    emergencyChemicals = owlClass;
                    break;
                }
            }
            if (emergencyChemicals != null) {
                for (OWLNamedIndividual instances : reasoner.getInstances(emergencyChemicals, false).getFlattened()) {
                    OWLObjectProperty objectProperty = df.getOWLObjectProperty("#" + "hasFlammabilityLevel", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasLevel", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            String x = chemicalDataProperty.getLiteral();
                            Integer chemicalLevel = Integer.parseInt(x);
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            flammabilityMap.put(label, chemicalLevel);
                        }
                    }
                    objectProperty = df.getOWLObjectProperty("#" + "hasInstabilityOrReactivityLevel", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasLevel", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            String x = chemicalDataProperty.getLiteral();
                            Integer chemicalLevel = Integer.parseInt(x);
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            instabilityMap.put(label, chemicalLevel);
                        }
                    }
                    objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidEye", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            eyeMap.put(label, alphabet);
                        }
                    }
                    objectProperty = df.getOWLObjectProperty("#" + "hasLargeFireFighting", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        Integer alphabet = 0;
                        String label = instances.toStringID();
                        for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                            if (annotation.getValue() instanceof OWLLiteral) {
                                OWLLiteral val = (OWLLiteral) annotation.getValue();
                                label = val.getLiteral();
                            }
                        }
                        fireMap.put(label, alphabet);
                    }
                    objectProperty = df.getOWLObjectProperty("#" + "hasSmallFireFighting", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        Integer alphabet = 0;
                        String label = instances.toStringID();
                        for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                            if (annotation.getValue() instanceof OWLLiteral) {
                                OWLLiteral val = (OWLLiteral) annotation.getValue();
                                label = val.getLiteral();
                            }
                        }
                        fireMap.put(label, alphabet);
                    }
                    objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidSeriousInhalation", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            inhalationMap.put(label, alphabet);
                        }
                    }
                    objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidInhalation", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            inhalationMap.put(label, alphabet);
                        }
                    }

                    objectProperty = df.getOWLObjectProperty("#" + "hasHealthLevel", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasLevel", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            String x = chemicalDataProperty.getLiteral();
                            Integer chemicalLevel = Integer.parseInt(x);
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            healthMap.put(label, chemicalLevel);
                        }
                    }

                    objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidSeriousSkin", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            skinMap.put(label, alphabet);
                        }
                    }

                    objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidSkin", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            skinMap.put(label, alphabet);
                        }
                    }

                    objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidIngestion", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                        OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                        for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            ingestionMap.put(label, alphabet);
                        }
                    }

                    objectProperty = df.getOWLObjectProperty("#" + "hasLargeSpill", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            spillMap.put(label, alphabet);
                    }

                    objectProperty = df.getOWLObjectProperty("#" + "hasSmallSpill", pm);
                    for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(instances, objectProperty).getFlattened()) {
                            Integer alphabet = 0;
                            String label = instances.toStringID();
                            for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label = val.getLiteral();
                                }
                            }
                            spillMap.put(label, alphabet);
                    }
                }
            }
        }

        private MyThread(InputStream y) {
            this.y = y;
        }
    }

    class MyThread2 extends Thread {
        InputStream z;

        @Override
        public void run() {
            OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
            try {
                o = oom.loadOntologyFromOntologyDocument(z);
            } catch (Exception e) {
                e.printStackTrace();
            }
            df = oom.getOWLDataFactory();
            Set<OWLClass> classes = o.getClassesInSignature(false);
            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
            OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
            OWLReasoner reasoner = reasonerFactory.createReasoner(o, config);
            reasoner.precomputeInferences();
            OWLClass chemicals = null;
            for (OWLClass owlClass : classes) {
                String x = owlClass.getIRI().toString();
                x = x.substring(x.lastIndexOf("#") + 1);
                if (x.equals("ProductName")) {
                    chemicals = owlClass;
                    break;
                }
            }
            if (chemicals != null)
                for (OWLIndividual indiv : chemicals.getIndividuals(o)) {
                    OWLEntity entity = (OWLEntity) indiv;
                    String label = indiv.toStringID();
                    for (OWLAnnotation annotation : entity.getAnnotations(o, df.getRDFSLabel())) {
                        if (annotation.getValue() instanceof OWLLiteral) {
                            OWLLiteral val = (OWLLiteral) annotation.getValue();
                            label = val.getLiteral();
                        }
                    }
                    chemicalMap.put(label, indiv);
                }

        }

        private MyThread2(InputStream z) {
            this.z = z;
        }
    }

    private void changeContent1() {
        typed = editText.getText().toString();
        emergencyMap.remove("Blue");
        emergencyMap.remove("Contamination");
        emergencyMap.remove("FirstAidEye");
        emergencyMap.remove("Ignition");
        emergencyMap.remove("Red");
        emergencyMap.remove("Yellow");
        emergencyMap.remove("White");
        emergencyMap.remove("Section006AccidentalReleaseMeasures");
        emergencyMap.remove("Section010StabilityAndReactivity");
        emergencyMap.remove("SpecialNotice");
        ArrayList<String> temp = new ArrayList<>(emergencyMap.keySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            temp.sort(String::compareTo);
        }
        current = new ArrayList<>();
        for (String str : temp) {
            String[] split = str.split("#");
            String toAdd = str;
            if (split.length > 1)
                toAdd = split[1].substring(0, split[1].lastIndexOf(">"));
            if (!typed.equals("") && !toAdd.toLowerCase().startsWith(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, current);
        listView.setAdapter(aao);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            state = 3;
            selected = listView.getItemAtPosition(position).toString();
            changeView();
        });
    }

    private void changeContent2() {
        typed = editTextChemical.getText().toString();
        ArrayList<String> temp = new ArrayList<>(chemicalMap.keySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            temp.sort(String::compareTo);
        }
        current = new ArrayList<>();
        for (String str : temp) {
            String[] split = str.split("#");
            String toAdd = str;
            if (split.length > 1)
                toAdd = split[1];
            if (!typed.equals("") && !toAdd.toLowerCase().contains(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, current);
        listView.setAdapter(aao);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(WhereIsYourEmergency.this, ThirdActivity.class);
            intent.putExtra("ChemicalName", listView.getItemAtPosition(position).toString());
            startActivity(intent);
        });
    }

    private void changeContent3() {
        typed = editTextChemical.getText().toString();
        if (selected.equals("Flammability")) {
            emergencyChemicalsMap = new HashMap<>();
            emergencyChemicalsMap = flammabilityMap;
        }
        if (selected.equals("Health")) {
            emergencyChemicalsMap = new HashMap<>();
            emergencyChemicalsMap = healthMap;
        }
        if (selected.equals("InstabilityOrReactivity")) {
            emergencyChemicalsMap = new HashMap<>();
            emergencyChemicalsMap = instabilityMap;
        }
        if (selected.equals("Eye Contact")) {
            emergencyChemicalsMap = new HashMap<>(healthMap);
            emergencyChemicalsMap.keySet().retainAll(eyeMap.keySet());
        }
        if (selected.equals("FireFighting")) {
            emergencyChemicalsMap = new HashMap<>(flammabilityMap);
            emergencyChemicalsMap.keySet().retainAll(fireMap.keySet());
        }
        if (selected.equals("Inhalation")) {
            emergencyChemicalsMap = new HashMap<>(healthMap);
            emergencyChemicalsMap.keySet().retainAll(inhalationMap.keySet());
        }
        if (selected.equals("Skin Contact")) {
            emergencyChemicalsMap = new HashMap<>(healthMap);
            emergencyChemicalsMap.keySet().retainAll(skinMap.keySet());
        }
        if (selected.equals("Spill")) {
            emergencyChemicalsMap = new HashMap<>(instabilityMap);
            emergencyChemicalsMap.keySet().retainAll(spillMap.keySet());
        }
        if (selected.equals("Ingestion")) {
            emergencyChemicalsMap = new HashMap<>(healthMap);
            emergencyChemicalsMap.keySet().retainAll(ingestionMap.keySet());
        }
        Map<String, Integer> tempSortedMap = new TreeMap<>(emergencyChemicalsMap);
        HashMap<String, Integer> finalSortedMap = new LinkedHashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tempSortedMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> finalSortedMap.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> temp = new ArrayList<>(finalSortedMap.keySet());
        ArrayList<Integer> temp2 = new ArrayList<>(finalSortedMap.values());
        current = new ArrayList<>();
        for (String str : temp) {
            String[] split = str.split("#");
            String toAdd = str;
            if (split.length > 1)
                toAdd = split[1];
            if (!typed.equals("") && !toAdd.toLowerCase().contains(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, current){
            private int position;
            private View convertView;
            private ViewGroup parent;

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                this.position = position;
                this.convertView = convertView;
                this.parent = parent;
                View row = super.getView(position, convertView, parent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if(temp2.get(position)== 4)
                    {
                        row.setBackgroundColor (Color.rgb(177,135,137));
                    }
                    if(temp2.get(position)== 3)
                    {
                        row.setBackgroundColor (Color.rgb(205,167,169));
                    }
                    if(temp2.get(position)== 2)
                    {
                        row.setBackgroundColor (Color.rgb(216,186,188));
                    }
                    if(temp2.get(position)== 1)
                    {
                        row.setBackgroundColor (Color.rgb(232,216,216));
                    }
                    if(temp2.get(position)==0){
                        row.setBackgroundColor (Color.rgb(236,234,235));
                    }
                }
                return row;
            }
        };
        listView.setAdapter(aao);
        if (selected.equals("Flammability") || selected.equals("Health") || selected.equals("InstabilityOrReactivity")) {
            listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                Intent intent = new Intent(WhereIsYourEmergency.this, ThirdActivity.class);
                intent.putExtra("ChemicalName", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            });
        } else {
            listView.setOnItemClickListener((parent, view, position, id) -> {
//                state = 4;
//                chemical = listView.getItemAtPosition(position).toString();
//                changeView();
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra("ChemicalName", listView.getItemAtPosition(position).toString());
                intent.putExtra("Selected", selected);
                startActivity(intent);
            });
        }
    }

    private void changeView() {
        if ((state != 0)&&(state!=4)) {
            abLogo1.setVisibility(View.GONE);
            abLogo2.setVisibility(View.GONE);
            abLogo3.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
            button5.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            typed = "";
            listView.setTextFilterEnabled(true);
            if (state == 1) {
                editText.setVisibility(View.VISIBLE);
                editTextChemical.setVisibility(View.GONE);
                changeContent1();
            }
            if (state == 2) {
                editText.setVisibility(View.GONE);
                editTextChemical.setVisibility(View.VISIBLE);
                changeContent2();
            }
            if (state == 3) {
                legend.setVisibility(View.VISIBLE);
                highLevel.setVisibility(View.VISIBLE);
                lowLevel.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);
                editTextChemical.setVisibility(View.VISIBLE);
                changeContent3();
            }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (state == 1) {
                        changeContent1();
                    }
                    if (state == 2) {
                        changeContent2();
                    }
                    if (state == 3) {
                        changeContent3();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            editTextChemical.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (state == 1) {
                        changeContent1();
                    }
                    if (state == 2) {
                        changeContent2();
                    }
                    if (state == 3) {
                        changeContent3();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        if (state == 0) {
            abLogo1.setVisibility(View.GONE);
            abLogo2.setVisibility(View.GONE);
            abLogo3.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            legend.setVisibility(View.GONE);
            highLevel.setVisibility(View.GONE);
            lowLevel.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            imageView1.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.GONE);
            button5.setVisibility(View.GONE);
            editTextChemical.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
        if(state==4){
            abLogo1.setVisibility(View.VISIBLE);
            abLogo2.setVisibility(View.VISIBLE);
            abLogo3.setVisibility(View.VISIBLE);
            textView5.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            button5.setVisibility(View.VISIBLE);
            editTextChemical.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            legend.setVisibility(View.GONE);
            highLevel.setVisibility(View.GONE);
            lowLevel.setVisibility(View.GONE);
        }
    }

    public void rate(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/q64HES"));
        startActivity(browserIntent);
    }

    public void home(View view) {
        state=0;
        changeView();
    }
}