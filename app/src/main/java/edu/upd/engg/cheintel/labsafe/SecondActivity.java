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
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import android.support.v7.widget.Toolbar;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {
    HashMap<String,OWLIndividual> map;
    List<String> current;
    String typed;
    ListView listView;
    EditText editText;
    OWLOntology o;
    OWLDataFactory df;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        typed = "";
        map = new HashMap<>();
        editText = findViewById(R.id.editText);
        mToolbar = findViewById(R.id.toolbar1);
        try {
            loadOntology();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mToolbar.setTitle(getIntent().getStringExtra("EmergencyName"));
        }
        changeContent();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeContent();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void changeContent() {
        typed = editText.getText().toString();
        ArrayList<String> temp = new ArrayList<>(map.keySet());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            temp.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }
        current = new ArrayList<>();
        for (String str : temp) {
            String[] split = str.split("#");
            String toAdd = str;
            if (split.length > 1)
                toAdd = split[1].substring(0,split[1].lastIndexOf(">"));
            if (!typed.equals("") && !toAdd.toLowerCase().startsWith(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,current);
        listView.setAdapter(aao);
    }
    private void loadOntology() throws Exception {
        OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
        o = oom.loadOntologyFromOntologyDocument(this.getAssets().open("OntologySplit1_v1.0.5.owl"));
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
            String emergency = getIntent().getStringExtra("EmergencyName");
            if (x.equals("ProductName")) {
                chemicals = owlClass;
                break;
            }
        }
        if (chemicals != null)
            for (OWLNamedIndividual instances : reasoner.getInstances(chemicals,false).getFlattened()) {
//                String label = instances.getIRI().getFragment();
                String label = instances.getObjectPropertyValues(o).toString();
                map.put(label,instances);
            }
    }
}