package edu.upd.engg.cheintel.labsafe;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.Toolbar;
    import android.view.View;

    import com.github.barteksc.pdfviewer.PDFView;

public class ThirdActivity extends AppCompatActivity {
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mToolbar = findViewById(R.id.toolbar1);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mToolbar.setTitle(getIntent().getStringExtra("ChemicalName"));
            if (mToolbar.getTitle().toString().equalsIgnoreCase("Acetone")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Acetone.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("3,5-Dinitrosalicylic Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("3,5-dinitrosalicylic acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Acetic Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Acetic Acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Activated Carbon")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Activated charcoal.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Agar-Agar")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("AgarAgar.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Aluminum Oxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("AluminumOxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ammonium Chloride")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ammonium chloride.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ammonium Hydroxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ammonium hydroxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ammonium Nitrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ammonium nitrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ammonium Oxalate Mononydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ammonium OxalateMonohydrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ammonium Sulfate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ammonium sulfate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Barium Chloride Anhydrous")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Barium chloride, anhydrous.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Barium Chloride Dihydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Barium chloride, dihydrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Barium Nitrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Barium nitrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Benzene")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Benzene.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Benzoic Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Benzoic acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Boric Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Boric acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Boron Trifluouride (10-15% in MeOH)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Boron Trifluoride 10-15_.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Buffer Solution (pH 4.00)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Buffer solution pH 4.00 (not scilab.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Buffer Solution (pH 7.00)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Buffer solution pH 7.00 (not scilab).pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Calcium Carbonate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Calcium Carbonate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Chloroform")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Chloroform.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Citric Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Citric acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Cobalt Chloride Solution")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Cobalt chloride solution.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Copper Sulfate (10% Solution)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Copper sulfate 10_.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Copper Sulfate Pentahydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Copper sulfate pentahydrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Cupric Chloride Dihydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("CupricChlorideDihydrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Cupric Oxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("CupricOxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Cuprous Chloride")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("CuprousChloride.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Dextrose Anhydrous")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("d-glucose.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Diethylene Glycol")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Diethylene glycol.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Diphenylamine")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Diphenylamine.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ethyl Acetate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ethyl acetate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ethyl Alcohol (200 Proof)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ethyl Alcohol 200 proof.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ferrous Ammonium Sulfate Hexahydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ferrous Ammonium Sulfate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Ferrous Sulfate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Ferrous Ammonium Sulfate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Formaldehyde (37% Solution)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Formaldehyde.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Formic Acid (85%)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("FormicAcid85.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Glycerin")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Glycerin.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Hydrogen Peroxide (30%)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Hydrogen Peroxide 30_.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Iodine")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Iodine.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Kaolin Colloidal Powder")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("KaolinPowder.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Lactose")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Lactose.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Lauric Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Lauric acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Magnesium Oxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("MagnesiumOxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Magnesium Sulfate Anhydrous")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Magnesium sulfate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Melamine")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Melamine.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Mercuric Oxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("MercuricOxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Mercuric Chloride")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Mercuric chloride.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Methyl Alcohol")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Methanol.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Naphthalene")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Naphthalene.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Oxalic Acid Anhydrous")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Oxalic Acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Peptone")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Peptone.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Peptone Water")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("PeptoneWater.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Phenolphthalien (0.5% in 50% Isopropanol)")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Phenolphthalein0.5percent.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Phosphorus Pentoxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("PhosphorusPentoxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Polyethylene Glyco 400")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Polypropylene_MSDS (not scilab).pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Polypropylene Glycol 2000")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("PolyproyleneGlycol2000.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Polysorbate 80")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("TWEEN or polysorbate 80.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Biphthalate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium Biphthalate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Chloride")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium chloride.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Dichromate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium dichromate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Hydroxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium hydroxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Iodide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium iodide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Phosphate Monobasic")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium phosphate, monobasic.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Potassium Sulfate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Potassium sulfate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Salicylic Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Salicylic acid.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Silver Nitrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Silver nitrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Azide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("SodiumAzide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Bisulfite")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Sodium bisulfite.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Hydroxide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Sodium hydroxide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Iodide")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Sodium iodide.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Lauryl Sulfate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Sodium Lauryl Sulfate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Phosphate Dibasic")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("SodiumPhosphateDibasic.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Sulfite")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Sodium sulfite.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sodium Thiosulfate Pentahydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Sodium thiosulfate pentahydrate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Sulfuric Acid")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("SulfuricAcidPure.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Tetrabutylammonium Chloride Hydrate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("tetrabutylammonium chloride.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Tin")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Tin.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Triethanolamine")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Triethanolamine.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Tris(hydroxymethyl)methylamine")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("tris hydroxymethy amino methane.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Vinyl Acetate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("VAM.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Water")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Water.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Zinc Acetate")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Zinc acetate.pdf").load();
            } else if (mToolbar.getTitle().toString().equalsIgnoreCase("Zinc Metal")) {
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromAsset("Zinc metal.pdf").load();
            }
        }
    }

    public void home (View view){
        Intent intent = new Intent(ThirdActivity.this, WhereIsYourEmergency.class);
        startActivity(intent);
    }
}