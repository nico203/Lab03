package ns.dam.isi.frsf.utn.edu.ar.lab03;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.sql.Date;
import java.util.Arrays;

public class CrearOfertaActivity extends AppCompatActivity implements View.OnClickListener {
    private Button crearTrabajoButton;
    private EditText nombreOfertaEditText;
    private EditText horasPresupuestadasEditText;
    private EditText precioMaximoHoraEditText;
    private Spinner categoriaSpinner;
    private CheckBox requiereInglesCheckBox;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);

        crearTrabajoButton = (Button) findViewById(R.id.crearTrabajoButton);
        crearTrabajoButton.setOnClickListener(this);
        nombreOfertaEditText = (EditText) findViewById(R.id.nombreOfertaEditText);
        categoriaSpinner = (Spinner) findViewById(R.id.categoriaSpinner);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(Categoria.CATEGORIAS_MOCK)) ;
        categoriaSpinner.setAdapter(adapter);
        horasPresupuestadasEditText = (EditText) findViewById(R.id.horasPresupuestadasEditText);
        precioMaximoHoraEditText = (EditText) findViewById(R.id.precioMaximoHoraEditText);
        requiereInglesCheckBox = (CheckBox) findViewById(R.id.requiereInglesCheckBox);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nombre", nombreOfertaEditText.getText().toString());
        outState.putInt("categoria", categoriaSpinner.getSelectedItemPosition());
        outState.putInt("tipoMoneda", radioGroup.getCheckedRadioButtonId());
        outState.putString("horasPresupuestadas", horasPresupuestadasEditText.getText().toString());
        outState.putString("precioMaximo", precioMaximoHoraEditText.getText().toString());
        outState.putBoolean("requiereIngles",requiereInglesCheckBox.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        nombreOfertaEditText.setText(savedInstanceState.getString("nombre"));
        categoriaSpinner.setSelection(savedInstanceState.getInt("categoria"));
        horasPresupuestadasEditText.setText(savedInstanceState.getString("horasPresupuestadas"));
        precioMaximoHoraEditText.setText(savedInstanceState.getString("precioMaximo"));
        requiereInglesCheckBox.setEnabled(savedInstanceState.getBoolean("requiereIngles"));
        radioGroup.check(savedInstanceState.getInt("tipoMoneda"));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() != R.id.crearTrabajoButton) {
            return;
        }
        Trabajo trabajo = new Trabajo();
        trabajo.setDescripcion(nombreOfertaEditText.getText().toString());
        trabajo.setCategoria((Categoria)categoriaSpinner.getSelectedItem());
        if(!horasPresupuestadasEditText.getText().toString().isEmpty())
            trabajo.setHorasPresupuestadas(Integer.parseInt(horasPresupuestadasEditText.getText().toString()));
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.usaRB:
                trabajo.setMonedaPago(1);
                break;
            case R.id.euroRB:
                trabajo.setMonedaPago(2);
                break;
            case R.id.argRB:
                trabajo.setMonedaPago(3);
                break;
            case R.id.ukRB:
                trabajo.setMonedaPago(4);
                break;
            case R.id.brRB:
                trabajo.setMonedaPago(5);
                break;
        }
        if(!precioMaximoHoraEditText.getText().toString().isEmpty())
            trabajo.setPrecioMaximoHora(Double.parseDouble(precioMaximoHoraEditText.getText().toString()));
        trabajo.setRequiereIngles(requiereInglesCheckBox.isChecked());

        Intent intent = getIntent();
        intent.putExtra("Resultado", trabajo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
