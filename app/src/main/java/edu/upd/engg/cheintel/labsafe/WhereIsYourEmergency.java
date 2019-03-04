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
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
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

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WhereIsYourEmergency extends AppCompatActivity {
    OWLOntology o;
    OWLDataFactory df;
    HashMap<String, OWLIndividual> chemicalMap;
    HashMap<String, OWLClass> emergencyMap;
    HashMap<String, OWLIndividual> emergencyChemicalMap;
    ImageView imageView1;
    Button button1;
    Button button2;
    Button button3;
    EditText editText;
    ListView listView;
    List<String> current;
    String typed;
    String state;

    @Override
    public void onBackPressed() {
        if (state.equals("0")) {
            super.onBackPressed();
        }
        if ((state.equals("1")) || (state.equals("2"))) {
            imageView1.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            state = "0";
        }
        if ((state.equals("3"))){
            imageView1.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            state = "1";
            typed = "";
            listView.setTextFilterEnabled(true);
            changeContent1();
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    changeContent1();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_is_your_emergency);
        state = "0";
        imageView1 = findViewById(R.id.imageView1);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        chemicalMap = new HashMap<>();
        emergencyMap = new HashMap<>();
        emergencyChemicalMap = new HashMap<>();
        ExecutorService pool = Executors.newFixedThreadPool(1);
        try {
            InputStream y = this.getAssets().open("OntologySplit1_v1.0.5.owl");
            MyThread t1 = new MyThread(y);
            pool.execute(t1);
            InputStream z = this.getAssets().open("OntologySplit2_v1.0.1.owl");
            MyThread t2 = new MyThread(z);
            pool.execute(t2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = "1";
                imageView1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                typed = "";
                listView.setTextFilterEnabled(true);
                changeContent1();
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        changeContent1();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = "2";
                imageView1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                listView.setTextFilterEnabled(true);
                changeContent2();
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        changeContent2();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        });
    }


    class MyThread extends Thread{
        OWLOntology o;
        InputStream y;
        @Override
        public void run() {
            OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
         try{
            o = oom.loadOntologyFromOntologyDocument(y);
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
            OWLClass emergencies = null;
            for (OWLClass owlClass : classes) {
                String z = owlClass.getIRI().toString();
                z = z.substring(z.lastIndexOf("#") + 1);
                if (z.equals("EmergencyProcedures")) {
                    emergencies = owlClass;
                    break;
                }
            }
            if (emergencies != null)
                for (OWLClass subClass : reasoner.getSubClasses(emergencies, true).getFlattened()) {
                    String label = subClass.getIRI().getFragment();
                    for (OWLAnnotation annotation : subClass.getAnnotations(o, df.getRDFSLabel())) {
                        if (annotation.getValue() instanceof OWLLiteral) {
                            OWLLiteral val = (OWLLiteral) annotation.getValue();
                            label=val.getLiteral();
                        }
                    }
                    emergencyMap.put(label, subClass);
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
            if (emergenciesx != null)
                for (OWLClass subClass : reasoner.getSubClasses(emergenciesx, true).getFlattened()) {
                    String label = subClass.getIRI().getFragment();
                    emergencyMap.put(label, subClass);
                }
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
                            label=val.getLiteral();
                        }
                    }
                    chemicalMap.put(label, indiv);
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
            if (emergencyChemicals != null)
                for (OWLNamedIndividual instances : reasoner.getInstances(emergencyChemicals,false).getFlattened()) {
                    String label = instances.getIRI().getFragment();
                    for (OWLAnnotation annotation : instances.getAnnotations(o, df.getRDFSLabel())) {
                        if (annotation.getValue() instanceof OWLLiteral) {
                            OWLLiteral val = (OWLLiteral) annotation.getValue();
                            label=val.getLiteral();
                        }
                    }
//                    HashSet<OWLObjectProperty> relatedObjectProperties = new HashSet<>();
//                    HashSet<OWLObjectPropertyExpression> subProperties = new HashSet<>();
//                    Set<OWLClass> types = reasoner.getTypes(instances, true).getFlattened();
//                    for (OWLObjectPropertyExpression property : subProperties) {
//                        Set<OWLClassExpression> domains = property.getDomains(o);
//                        for (OWLClassExpression domain : domains) {
//                            if (types.contains(domain.asOWLClass())) {
//                                relatedObjectProperties.add(property.asOWLObjectProperty());
//                            }
//                        }
//                    }
//                    String label = relatedObjectProperties.toString();
//                    String label = instances.getObjectPropertyValues(o).toString();
                    emergencyChemicalMap.put(label,instances);
                }
        }
        public MyThread (InputStream y) {
            this.y=y;
        }
    }

    private void changeContent1() {
        typed = editText.getText().toString();
        ArrayList<String> temp = new ArrayList<>(emergencyMap.keySet());
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
                toAdd = split[1].substring(0, split[1].lastIndexOf(">"));
            if (!typed.equals("") && !toAdd.toLowerCase().startsWith(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, current);
        listView.setAdapter(aao);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state = "3";
                imageView1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                typed = "";
                listView.setTextFilterEnabled(true);
                changeContent3();
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        changeContent3();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
    }

    private void changeContent2() {
        typed = editText.getText().toString();
        ArrayList<String> temp = new ArrayList<>(chemicalMap.keySet());
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
                toAdd = split[1];
            if (!typed.equals("") && !toAdd.toLowerCase().contains(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, current);
        listView.setAdapter(aao);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WhereIsYourEmergency.this, ThirdActivity.class);
                intent.putExtra("ChemicalName", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }

    private void changeContent3() {
        typed = editText.getText().toString();
        ArrayList<String> temp = new ArrayList<>(emergencyChemicalMap.keySet());
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
                toAdd = split[1];
            if (!typed.equals("") && !toAdd.toLowerCase().contains(typed.toLowerCase()))
                continue;
            current.add(toAdd);
        }
        ArrayAdapter<String> aao = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, current);
        listView.setAdapter(aao);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(WhereIsYourEmergency.this, ThirdActivity.class);
//                intent.putExtra("ChemicalName", listView.getItemAtPosition(position).toString());
//                startActivity(intent);
//            }
//        });
    }


    public void rate(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/q64HES"));
        startActivity(browserIntent);
    }


}