package fish.com.fishapp.fish.com.fishapp.controlador;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import fish.com.fishapp.DAO.DENUNCIASDao;
import fish.com.fishapp.DAO.DaoMaster;
import fish.com.fishapp.DAO.DaoSession;
import fish.com.fishapp.DAO.EMBARCACION;
import fish.com.fishapp.DAO.EMBARCACIONDao;
import fish.com.fishapp.R;

public class BusqAvanzada extends AppCompatActivity {
    public Spinner spinner_socios ;
    public Spinner spinner_banderas ;
    public Spinner spinner_tipos ;
    public DaoMaster.DevOpenHelper helper;
    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public EMBARCACIONDao embarcacionDao;
    Button button;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busq_avanzada);
        setTitle("Resultados");
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String nombre = bundle.getString("nombre");
        String matricula = bundle.getString("matricula");
        String tipo = bundle.getString("tipo");
        String Bandera = bundle.getString("bandera");

        helper = new DaoMaster.DevOpenHelper(this, "fish_db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        embarcacionDao = daoSession.getEMBARCACIONDao();
        List<EMBARCACION> filtro = listFiltro(nombre,matricula,tipo,Bandera);
        ArrayAdapter<EMBARCACION> adapter2;
        adapter2 = new ArrayAdapter<EMBARCACION>(this,android.R.layout.simple_list_item_1,filtro);
        listView = (ListView) this.findViewById(R.id.lt_filtro);
        listView.setAdapter(adapter2);
    }
    public List<EMBARCACION> listFiltro(String nombre,String matricula,String tipo,String Bandera) {
        QueryBuilder qb = embarcacionDao.queryBuilder();
        qb.where(EMBARCACIONDao.Properties.EMB_BANDERA.eq(Bandera),
                qb.and(EMBARCACIONDao.Properties.EMB_TIPO.eq(tipo),
                        qb.and(EMBARCACIONDao.Properties.EMB_NOM_EMBARCACION.eq(nombre), EMBARCACIONDao.Properties.EMB_NUM_REGISTRO.eq(matricula))));
        List query = qb.list();
        return query;
    }
}
