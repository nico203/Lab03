package ns.dam.isi.frsf.utn.edu.ar.lab03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by NicolasAndres on 4/10/2016.
 */
public class TrabajoAdapter extends ArrayAdapter<Trabajo> {
    LayoutInflater inflater;

    public TrabajoAdapter(Context context, List<Trabajo> items) {
        super(context, R.layout.item_view, items);
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View item = (convertView == null) ? inflater.inflate(R.layout.item_view, parent, false) : convertView;

        ViewHolder holder = (ViewHolder) item.getTag();
        if(holder == null) {
            holder = new ViewHolder(item);
            item.setTag(holder);
        }

        Trabajo trabajo = this.getItem(position);
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");

        holder.categoriaTextView.setText(trabajo.getCategoria().getDescripcion());
        holder.nombreProyectoTextView.setText(trabajo.getDescripcion());
        holder.infoProyectoTextView.setText("Horas: " + trabajo.getHorasPresupuestadas() + " Max $/Hora: " + df.format(trabajo.getPrecioMaximoHora()));
        switch (trabajo.getMonedaPago()) {
            case 1:
                holder.imageView.setImageResource(R.drawable.usa);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.eu);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.arg);
                break;
            case 4:
                holder.imageView.setImageResource(R.drawable.uk);
                break;
            case 5:
                holder.imageView.setImageResource(R.drawable.br);
                break;
        }
        holder.fechaFinTextView.setText("Fecha Fin: " + dt.format(trabajo.getFechaEntrega()));
        holder.inglesCheckBox.setChecked(trabajo.getRequiereIngles());
        holder.inglesCheckBox.setEnabled(false);

        return item;
    }
}
