package edu.upd.engg.cheintel.labsafe;

import android.content.Intent;
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
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WhereIsYourEmergency extends AppCompatActivity {
    OWLOntology o;
    OWLDataFactory df;
    HashMap<String, OWLIndividual> map;
    HashMap<String,OWLClass> emergencyMap;
    HashMap<String, OWLIndividual> repeatMap;
    ImageView imageView1;
    Button button1;
    Button button2;
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
        if ((state.equals("1"))||(state.equals("2"))) {
            imageView1.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            state = "0";
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
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        map = new HashMap<>();
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        emergencyMap = new HashMap<>();
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = "1";
                imageView1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
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

    //Ontology
//    Thread t1 = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            for (int i = 0; i < 10000; i++) {
//                LMLog.info("THREAD", "Counting " + i + " in Thread A");
//            }
//        }
//    });

    public void run() throws Exception {
        OWLOntologyManager oom = OWLManager.createOWLOntologyManager();
        o = oom.loadOntologyFromOntologyDocument(this.getAssets().open("Ontology_v1.0.4.owl"));
        df = oom.getOWLDataFactory();
        Set<OWLClass> classes = o.getClassesInSignature(false);
        for (OWLClass owlClass:classes){
            String label = "";
            for (OWLAnnotation anno:owlClass.getAnnotations(o,df.getRDFSLabel())){
                if (anno.getValue() instanceof OWLLiteral) {
                    label = ((OWLLiteral) anno.getValue()).getLiteral();
                }
            }
            if (label.equals(""))
                emergencyMap.put(owlClass.toString(),owlClass);
            else
                emergencyMap.put(label,owlClass);
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
                for (OWLAnnotation anno : entity.getAnnotations(o)) {
                    label = anno.getValue().toString();
                    label = label.substring(1, label.lastIndexOf("\""));
                    break;
                }
                map.put(label, indiv);
//                for (OWLIndividual similar : indiv.getSameIndividuals(o)) {
//                String same = indiv.toStringID();
//                Set<OWLDataProperty> odp = indiv.getDataPropertiesInSignature();
//                for (OWLDataProperty odp : indiv.getDataPropertyValues(odp,o)) {
//                    same = indiv.getSameIndividuals(o).toString();
//                    same = same.substring(1, same.lastIndexOf("\""));
//                    break;
//                }
//                repeatMap.put(same, indiv);
            }
//            }
    }

    private void changeContent1() {
        typed = editText.getText().toString();
        ArrayList<String> temp = new ArrayList<>(emergencyMap.keySet());
//        List<String> temp = temp1;
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
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(WhereIsYourEmergency.this, TryActivity.class);
//                intent.putExtra("EmergencyName", listView.getItemAtPosition(position).toString());
//                startActivity(intent);
//            }
//        });
    }

    private void changeContent2() {
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



}