package edu.upd.engg.cheintel.labsafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import android.support.v7.widget.Toolbar;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SecondActivity extends AppCompatActivity {
    HashMap<String, Integer> map;
    List<String> current;
    String typed;
    ListView listView;
    OWLOntology o;
    OWLDataFactory df;
    Toolbar mToolbar;
    String chemical;
    String selected;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        typed = "";
        map = new HashMap<>();
        mToolbar = findViewById(R.id.toolbar1);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            chemical = getIntent().getStringExtra("ChemicalName");
            selected = getIntent().getStringExtra("Selected");
            mToolbar.setTitle(chemical+ " Emergency Steps");
        }
        try {
            loadOntology();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);
        changeContent();
    }

    private void changeContent() {
        Map<String, Integer> tempSortedMap = new TreeMap<>(map);
        HashMap<String, Integer> finalSortedMap = new LinkedHashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tempSortedMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered(x -> finalSortedMap.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> temp = new ArrayList<>(finalSortedMap.keySet());
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
    }

    private void loadOntology() throws Exception {
        OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
        o = oom.loadOntologyFromOntologyDocument(this.getAssets().open("OntologySplit1_v1.0.8.owl"));
        df = oom.getOWLDataFactory();
        String base = "http://www.semanticweb.org/thomasm.lutao/azescobar/ontologies/2017/9/MSDSOntologySplit1-nopropsvers";
        PrefixManager pm = new DefaultPrefixManager(base);
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
            for (OWLNamedIndividual indiv : reasoner.getInstances(chemicals, false).getFlattened()) {
                String label = indiv.toStringID();
                for (OWLAnnotation annotation : indiv.getAnnotations(o, df.getRDFSLabel())) {
                    if (annotation.getValue() instanceof OWLLiteral) {
                        OWLLiteral val = (OWLLiteral) annotation.getValue();
                        label = val.getLiteral();
                    }
                }
                if (label.equals(chemical)) {
                    if (selected.equals("Eye Contact")) {
                        OWLObjectProperty objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidEye", pm);
                        for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(indiv, objectProperty).getFlattened()) {
                            OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                            for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                                String label2 = chemicalObjectProperty.getIRI().getFragment();
                                String x = chemicalDataProperty.getLiteral();
                                Integer chemicalLevel = Integer.parseInt(x);
                                for (OWLAnnotation annotation : chemicalObjectProperty.getAnnotations(o, df.getRDFSLabel())) {
                                    if (annotation.getValue() instanceof OWLLiteral) {
                                        OWLLiteral val = (OWLLiteral) annotation.getValue();
                                        label2 = val.getLiteral();
                                    }
                                }
                                map.put(label2, chemicalLevel);
                            }
                        }
                    }
                    if (selected.equals("FireFighting")) {
                        OWLObjectProperty objectProperty = df.getOWLObjectProperty("#" + "hasLargeFireFighting", pm);
                        for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(indiv, objectProperty).getFlattened()) {
                            String label2 = chemicalObjectProperty.getIRI().getFragment();
                            Integer chemicalLevel = 10;
                            for (OWLAnnotation annotation : chemicalObjectProperty.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label2 = val.getLiteral();
                                }
                            }
                            chemicalLevel++;
                            map.put(label2, chemicalLevel);
                        }
                        objectProperty = df.getOWLObjectProperty("#" + "hasSmallFireFighting", pm);
                        for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(indiv, objectProperty).getFlattened()) {
                            String label2 = chemicalObjectProperty.getIRI().getFragment();
                            Integer chemicalLevel = 0;
                            for (OWLAnnotation annotation : chemicalObjectProperty.getAnnotations(o, df.getRDFSLabel())) {
                                if (annotation.getValue() instanceof OWLLiteral) {
                                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                                    label2 = val.getLiteral();
                                }
                            }
                            chemicalLevel++;
                            map.put(label2, chemicalLevel);
                        }
                    }
                    if (selected.equals("Inhalation")) {
                        OWLObjectProperty objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidSeriousInhalation", pm);
                        for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(indiv, objectProperty).getFlattened()) {
                            OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                            for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                                String label2 = chemicalObjectProperty.getIRI().getFragment();
                                String x = chemicalDataProperty.getLiteral();
                                Integer chemicalLevel = Integer.parseInt(x);
                                for (OWLAnnotation annotation : chemicalObjectProperty.getAnnotations(o, df.getRDFSLabel())) {
                                    if (annotation.getValue() instanceof OWLLiteral) {
                                        OWLLiteral val = (OWLLiteral) annotation.getValue();
                                        label2 = val.getLiteral();
                                    }
                                }
                                map.put(label2, chemicalLevel);
                            }
                        }
                    }
                    if (selected.equals("Skin Contact")) {
                        OWLObjectProperty objectProperty = df.getOWLObjectProperty("#" + "hasFirstAidSeriousSkin", pm);
                        for (OWLNamedIndividual chemicalObjectProperty : reasoner.getObjectPropertyValues(indiv, objectProperty).getFlattened()) {
                            OWLDataProperty dataProperty = df.getOWLDataProperty("#" + "hasStep", pm);
                            for (OWLLiteral chemicalDataProperty : reasoner.getDataPropertyValues(chemicalObjectProperty, dataProperty)) {
                                String label2 = chemicalObjectProperty.getIRI().getFragment();
                                String x = chemicalDataProperty.getLiteral();
                                Integer chemicalLevel = Integer.parseInt(x);
                                for (OWLAnnotation annotation : chemicalObjectProperty.getAnnotations(o, df.getRDFSLabel())) {
                                    if (annotation.getValue() instanceof OWLLiteral) {
                                        OWLLiteral val = (OWLLiteral) annotation.getValue();
                                        label2 = val.getLiteral();
                                    }
                                }
                                map.put(label2, chemicalLevel);
                            }
                        }
                    }
                }
            }
    }
}