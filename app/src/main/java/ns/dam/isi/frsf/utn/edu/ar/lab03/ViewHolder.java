package ns.dam.isi.frsf.utn.edu.ar.lab03;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    TextView categoriaTextView = null;
    TextView nombreProyectoTextView = null;
    TextView infoProyectoTextView = null;
    ImageView imageView = null;
    TextView fechaFinTextView = null;
    CheckBox inglesCheckBox = null;

    public ViewHolder(View base) {
        this.categoriaTextView = (TextView) base.findViewById(R.id.categoriaTextView);
        this.nombreProyectoTextView = (TextView) base.findViewById(R.id.nombreProyectoTextView);
        this.infoProyectoTextView = (TextView) base.findViewById(R.id.infoProyectoTextView);
        this.imageView = (ImageView) base.findViewById(R.id.imageView);
        this.fechaFinTextView = (TextView) base.findViewById(R.id.fechaFinTextView);
        this.inglesCheckBox = (CheckBox) base.findViewById(R.id.inglesCheckBox);
    }
}
