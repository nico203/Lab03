package ns.dam.isi.frsf.utn.edu.ar.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ListView listView;
    private Toast toast;

    static final int ALTA_TRABAJO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        ArrayList<Trabajo> lista = new ArrayList<>(Arrays.asList(Trabajo.TRABAJOS_MOCK));
        TrabajoAdapter adapter = new TrabajoAdapter(getApplicationContext(), lista);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);

        registerForContextMenu(listView);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() != R.id.itemMenuPrincipal1) {
            return true;
        }

        Intent intent = new Intent(this, CrearOfertaActivity.class);
        startActivityForResult(intent, ALTA_TRABAJO);

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(((Trabajo)(listView.getAdapter().getItem(info.position))).getDescripcion());

        inflater.inflate(R.menu.menucontextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idMenuContextual1:
                toast.setText("Su postulación ha sido registrada correctamente");
                toast.show();
                break;
            case R.id.idMenuContextual2:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Trabajo trabajo = (Trabajo) listView.getAdapter().getItem(info.position);
                DecimalFormat df = new DecimalFormat("#.##");
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
                String moneda = "";
                switch (trabajo.getMonedaPago()) {
                    case 1:
                        moneda = "USD";
                        break;
                    case 2:
                        moneda = "EUR";
                        break;
                    case 3:
                        moneda = "ARS";
                        break;
                    case 4:
                        moneda = "GBP";
                        break;
                    case 5:
                        moneda = "BRL";
                        break;
                }

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        "-- Oferta de trabajo en WorkFromHome --\n\n" +
                        "Puesto: " + trabajo.getCategoria().getDescripcion() + "\n" +
                        "Proyecto: " + trabajo.getDescripcion() + "\n" +
                        "Horas: " + trabajo.getHorasPresupuestadas() + " Max $/Hora: " + df.format(trabajo.getPrecioMaximoHora()) + " " + moneda + "\n" +
                        "Fecha Fin: " + dt.format(trabajo.getFechaEntrega()) + "\n" +
                        "Requiere Inglés: " + (trabajo.getRequiereIngles() ? "Si" : "No"));

                startActivity(Intent.createChooser(intent, "Compartir"));
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     /*   if(requestCode != ALTA_TRABAJO || requestCode != RESULT_OK) {
            return;
        }*/
        try {
            toast.setText("Nuevo trabajo agregado");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

            Trabajo nuevoTrabajo = (Trabajo) data.getSerializableExtra("Resultado");

            TrabajoAdapter adapter = (TrabajoAdapter) listView.getAdapter();
            nuevoTrabajo.setId(adapter.getCount() + 1);
            adapter.add(nuevoTrabajo);
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            toast.setText("Hubo un error al intentar agregar un trabajo");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
