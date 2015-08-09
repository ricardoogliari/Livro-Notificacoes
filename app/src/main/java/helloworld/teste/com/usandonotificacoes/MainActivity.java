package helloworld.teste.com.usandonotificacoes;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imgIconeEscolhido;
    private int idEscolhido;

    private EditText edtContentTitle;
    private EditText edtContentText;
    private EditText edtAtualizacoes;

    private RadioButton rdbNormal;

    private TextView txtEstilos;
    private RadioGroup rgGroupEstilos;

    private LinearLayout llAtualizacoes;
    private RadioButton rdbBigPicture;
    private RadioButton rdbBigText;
    private RadioButton rdbInbox;

    private RadioButton rdbIndeterminado;
    private RadioButton rdbDeterminado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgIconeEscolhido = (ImageView) findViewById(R.id.imgIconeEscolhido);
        edtContentText = (EditText) findViewById(R.id.edtContentText);
        edtContentTitle = (EditText) findViewById(R.id.edtContentTitle);
        edtAtualizacoes = (EditText) findViewById(R.id.edtAtualizacoes);

        rdbBigPicture = (RadioButton) findViewById(R.id.rdbBigPicture);
        rdbBigText = (RadioButton) findViewById(R.id.rdbBigText);
        rdbInbox = (RadioButton) findViewById(R.id.rdbInbox);

        rdbDeterminado = (RadioButton) findViewById(R.id.rdbProgressDeterminado);
        rdbIndeterminado = (RadioButton) findViewById(R.id.rdbProgressIndeterminado);

        txtEstilos = (TextView) findViewById(R.id.txtEstilos);
        rgGroupEstilos = (RadioGroup) findViewById(R.id.groupEstilos);

        llAtualizacoes = (LinearLayout) findViewById(R.id.llAtualizacoes);

        rdbNormal = (RadioButton) findViewById(R.id.rdbNormal);
        rdbNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    txtEstilos.setVisibility(View.GONE);
                    rgGroupEstilos.setVisibility(View.GONE);
                } else {
                    txtEstilos.setVisibility(View.VISIBLE);
                    rgGroupEstilos.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void imgCompasso(View v) {
        idEscolhido = android.R.drawable.ic_menu_compass;
        imgIconeEscolhido.setImageResource(android.R.drawable.ic_menu_compass);
    }

    public void imgAgenda(View v) {
        idEscolhido = android.R.drawable.ic_menu_agenda;
        imgIconeEscolhido.setImageResource(android.R.drawable.ic_menu_agenda);
    }

    public void imgAdd(View v) {
        idEscolhido = android.R.drawable.ic_menu_add;
        imgIconeEscolhido.setImageResource(android.R.drawable.ic_menu_add);
    }

    public void imgAlways(View v) {
        idEscolhido = android.R.drawable.ic_menu_always_landscape_portrait;
        imgIconeEscolhido.setImageResource( android.R.drawable.ic_menu_always_landscape_portrait);
    }

    public void imgCamera(View v) {
        idEscolhido = android.R.drawable.ic_menu_camera;
        imgIconeEscolhido.setImageResource(android.R.drawable.ic_menu_camera);
    }

    public void gerarNotificacao(View v) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(idEscolhido);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), idEscolhido));
        builder.setContentTitle(edtContentTitle.getText());
        builder.setContentText(edtContentText.getText());

        builder.addAction(android.R.drawable.ic_lock_idle_alarm, "Comprar", null);
        builder.addAction(android.R.drawable.ic_lock_idle_charging, "Vender", null);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_camera,
                        "Câmera", null)
                        .build();
        builder.extend(new NotificationCompat.WearableExtender().addAction(action));

        final NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (!rdbNormal.isChecked()) {
            if (rdbBigPicture.isChecked()) {
                NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
                bigPicture.bigPicture(BitmapFactory.decodeResource(
                        getResources(), idEscolhido));
                bigPicture.setBigContentTitle(edtContentTitle.getText());
                bigPicture.setSummaryText(edtContentText.getText());
                builder.setStyle(bigPicture);
            } else if (rdbBigText.isChecked()) {
                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.bigText("Este é um exemplo de um grande texto contento o estilo BigTextStyke, oque achou? Acha que pode fazer bom proveito deste estilo? Que bom!");
                bigText.setBigContentTitle(edtContentTitle.getText());
                bigText.setSummaryText(edtContentText.getText());
                builder.setStyle(bigText);
            } else {
                NotificationCompat.InboxStyle inbox = new NotificationCompat.InboxStyle();
                inbox.addLine("Linha 1");
                inbox.addLine("Linha 2");
                inbox.addLine("Linha 3");
                inbox.addLine("Linha 4");
                inbox.addLine("Linha 5");
                inbox.setBigContentTitle(edtContentTitle.getText());
                inbox.setSummaryText(edtContentText.getText());

                builder.setStyle(inbox);
            }
        }

        if (rdbDeterminado.isChecked() || rdbIndeterminado.isChecked()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int incr;
                    for (incr = 0; incr <= 50; incr++) {
                        if (rdbIndeterminado.isChecked()){
                            builder.setProgress(0, 0, true);
                        } else {
                            builder.setProgress(50, incr, false);
                        }

                        nMgr.notify(19, builder.build());


                        try {
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e) {	}
                    }

                    builder.setContentText("Completo").setProgress(0,0, false);
                    nMgr.notify(19, builder.build());
                }
            }).start();
        } else {
            String atualizacoes = edtAtualizacoes.getText().toString();
            if (atualizacoes.trim().length() > 0) {
                int intAtualizacoes = Integer.parseInt(atualizacoes);
                for (int i = 0; i <= intAtualizacoes; i++) {
                    builder.setNumber(i);
                    nMgr.notify(10, builder.build());
                }
            } else {
                nMgr.notify(10, builder.build());
            }
        }
    }
}
