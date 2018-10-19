package com.example.vinic.testefirebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.URL;
import java.net.MalformedURLException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    int cont = 1;
    String gabaritoA;
    String gabaritoB;
    String gabaritoC;
    String gabaritoD;
    String perguntaAnteriror;
    String value;

    TextView campoPergunta;

    RadioGroup radiogroup = findViewById(R.id.radioGroup);
    RadioButton alternativaA;
    RadioButton alternativaB;
    RadioButton alternativaC;
    RadioButton alternativaD;

    Button responder;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefPergunta;
    DatabaseReference myRefResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //(RadioButton)radioGroup.getChildAt(id);
        //alternativaA = findViewById(R.id.alternativaA);

        radiogroup = findViewById(R.id.radioGroup);
        myRefPergunta = database.getReference("pergunta");
        myRefResposta = database.getReference("respostas");
        campoPergunta = findViewById(R.id.campoPergunta);
        radiogroup = findViewById(R.id.radioGroup);
        alternativaA = findViewById(R.id.alternativaA);
        alternativaB = findViewById(R.id.alternativaB);
        alternativaC = findViewById(R.id.alternativaC);
        alternativaD = findViewById(R.id.alternativaD);

        responder = findViewById(R.id.btnResposta);

        //Pegando a pergunta do firebase e mostrando na tela
        myRefPergunta.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                perguntaAnteriror = value;
                campoPergunta.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Pegando as alternativas e mostrando na tela
        //myRefPergunta.child("0").addValueEventListener(new ValueEventListener() {
        ValueEventListener valueEventListener = myRefResposta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot respostaSnapshot) {

                radiogroup.clearCheck();
                alternativaA.setTextColor(getResources().getColor(R.color.Black));
                alternativaB.setTextColor(getResources().getColor(R.color.Black));
                alternativaC.setTextColor(getResources().getColor(R.color.Black));
                alternativaD.setTextColor(getResources().getColor(R.color.Black));
                cont = 1;

                Iterable<DataSnapshot> respostaChildren = respostaSnapshot.getChildren();
                for (DataSnapshot resposta : respostaChildren) {

                    String tituloResposta = resposta.child("0").getValue().toString();
                    String respostaCorreta = resposta.child("1").getValue().toString();

                    switch (cont) {
                        case 1:
                            alternativaA.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoA = "alternativa A";
                            } else {
                                gabaritoA = "";
                            }
                        case 2:
                            alternativaB.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoB = "alternativa B";
                            } else {
                                gabaritoB = "";
                            }
                        case 3:
                            alternativaC.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoC = "alternativa C";
                            } else {
                                gabaritoC = "";
                            }
                        case 4:
                            alternativaD.setText(tituloResposta);
                            if (respostaCorreta.equals("true")) {
                                gabaritoD = "alternativa D";
                            } else {
                                gabaritoD = "";
                            }

                    }
                    cont++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Botão de responder, assim que cllicar muda a cor da certa pra verde e da errada para vermelho, porem precisa ser tratado para voltar a cor assim que mudar a pergunta
        //Falta implementar o POST para o web service para avisar que a pergunta foi respondida
        responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                String url ="https://www.evonegocios.com/api_pi/alguem_respondeu.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(String response) {

                                if (alternativaA.isChecked() && gabaritoA.equals("alternativa A")) {
                                    ToothReadWrite.WriteBuffer((byte) 1);
                                    //alternativaA.setBackgroundResource(R.color.Green);
                                    alternativaA.setTextColor(getResources().getColor(R.color.Green));
                                    Context contexto = getApplicationContext();
                                    String texto = "Correto";
                                    int duracao = Toast.LENGTH_LONG;
                                    Toast toast = Toast.makeText(contexto, texto,duracao);
                                    toast.show();
                                }else{
                                    if(gabaritoB.equals("alternativa B")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }else if(gabaritoC.equals("alternativa C")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    } else if (gabaritoD.equals("alternativa D")) {
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Green));
                                    }
                                }

                                if (alternativaB.isChecked() & gabaritoB.equals("alternativa B")) {
                                    ToothReadWrite.WriteBuffer((byte) 1);
                                    //campoPergunta.setText("alternativa B");
                                    alternativaB.setTextColor(getResources().getColor(R.color.Green));
                                }else{
                                    if(gabaritoA.equals("alternativa A")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }else if(gabaritoC.equals("alternativa C")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    } else if (gabaritoD.equals("alternativa D")) {
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Green));
                                    }
                                }

                                if (alternativaC.isChecked() & gabaritoC.equals("alternativa C")) {
                                    ToothReadWrite.WriteBuffer((byte) 1);
                                    //campoPergunta.setText("alternativa C");
                                    alternativaC.setTextColor(getResources().getColor(R.color.Green));
                                }else{
                                    if(gabaritoA.equals("alternativa A")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }else if(gabaritoB.equals("alternativa B")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }else if (gabaritoD.equals("alternativa D")) {
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Green));
                                    }
                                }

                                if (alternativaD.isChecked() & gabaritoD.equals("alternativa D")) {
                                    ToothReadWrite.WriteBuffer((byte) 1);
                                    //campoPergunta.setText("alternativa D");
                                    alternativaD.setTextColor(getResources().getColor(R.color.Green));
                                }else{
                                    if(gabaritoA.equals("alternativa A")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }else if(gabaritoB.equals("alternativa B")){
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }else if (gabaritoC.equals("alternativa C")) {
                                        alternativaA.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaB.setTextColor(getResources().getColor(R.color.Red));
                                        alternativaC.setTextColor(getResources().getColor(R.color.Green));
                                        alternativaD.setTextColor(getResources().getColor(R.color.Red));
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("Serviço indisponível");
                    }

                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
