package com.example.projetopdm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetopdm.database.DadosOpenHelper;
import com.example.projetopdm.database.Session;
import com.example.projetopdm.dominios.entidades.Procedimento;
import com.example.projetopdm.dominios.entidades.repositorios.ProcedimentoRepo;
import com.example.projetopdm.dominios.entidades.repositorios.UsuarioRepo;


public class MainActivity extends AppCompatActivity {

    ConstraintLayout activity_main;

    EditText et_email, et_senha;
    Button bt_entrar, bt_cadastrar;

    static SQLiteDatabase conexao;
    static DadosOpenHelper dadosOpenHelper;

    UsuarioRepo usuarioRepo = new UsuarioRepo(conexao);
    Session session;

    ProcedimentoRepo procedimentoRepo;
    Procedimento procedimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        activity_main = findViewById(R.id.activity_main);

        criarConexao();

        //descomentar na primeira vez que rodar o app pra poder inserir os procedimentos
        /*procedimentoRepo = new ProcedimentoRepo(conexao);
        procedimento = new Procedimento();

        procedimento.nome = "Massagem";
        procedimento.valor = 50.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Pintar o cabelo";
        procedimento.valor = 80.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Pintar as unhas";
        procedimento.valor = 40.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Esfolia????o";
        procedimento.valor = 35.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Limpeza de pele";
        procedimento.valor = 120.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Maquiagem";
        procedimento.valor = 65.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Cortar o cabelo";
        procedimento.valor = 50.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Alisamento";
        procedimento.valor = 250.00;
        procedimentoRepo.inserir(procedimento);
        procedimento.nome = "Depila????o";
        procedimento.valor = 70.00;
        procedimentoRepo.inserir(procedimento);*/

        bt_entrar = findViewById(R.id.bt_entrar);
        bt_cadastrar = findViewById(R.id.bt_cadastro);

        et_email = findViewById(R.id.email);
        et_senha = findViewById(R.id.valor);

        session = new Session(getBaseContext());

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String senha = et_senha.getText().toString();

                if (usuarioRepo.validaSenha(senha, email)) {

                    session.setID(usuarioRepo.buscarUsuario(email).ID);
                    session.setEmail(usuarioRepo.buscarUsuario(email).email);

                    Intent i = new Intent(getBaseContext(), UsuarioActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Usu??rio n??o cadastrado.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CadastroActivity.class);
                startActivity(i);
            }
        });

    }


    public void criarConexao() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);

            conexao = dadosOpenHelper.getWritableDatabase();

            //Snackbar.make(activity_main, R.string.message_conexao_ok, Snackbar.LENGTH_LONG).setAction(R.string.message_ok, null).show();

            usuarioRepo = new UsuarioRepo(conexao);

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.message_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.message_ok, null);
            dlg.show();
        }
    }
}