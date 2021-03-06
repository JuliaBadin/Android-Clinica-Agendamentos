package com.example.projetopdm.dominios.entidades.repositorios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetopdm.dominios.entidades.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepo {

    private SQLiteDatabase conexao;

    public AgendamentoRepo(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Agendamento agendamento){

        ContentValues contentValues = new ContentValues();
        contentValues.put("Dia", agendamento.dia);
        contentValues.put("Hora", agendamento.hora);
        contentValues.put("Usuario_ID", agendamento.usuario_id);
        contentValues.put("Procedimento_ID", agendamento.procedimento_id);
        contentValues.put("Procedimento", agendamento.procedimento);
        contentValues.put("Valor", agendamento.valor);

        conexao.insertOrThrow("Agendamento", "ID", contentValues);
    }

    public void excluir(int id){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        conexao.delete("Agendamento", "ID = ?", parametros);
    }

    public void alterar(Agendamento agendamento){

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", agendamento.ID);
        contentValues.put("Dia", agendamento.dia);
        contentValues.put("Hora", agendamento.hora);
        contentValues.put("Procedimento_ID", agendamento.procedimento_id);
        contentValues.put("Procedimento", agendamento.procedimento);
        contentValues.put("Valor", agendamento.valor);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(agendamento.ID);

        conexao.update("Agendamento", contentValues, "ID = ?", parametros);
    }

    public List<Agendamento> buscarAgendamentos() {

        List<Agendamento> agendamentos = new ArrayList<>();

        String sql = "SELECT * FROM Agendamento";
        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                Agendamento agendamento = new Agendamento();
                agendamento.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
                agendamento.usuario_id = resultado.getInt(resultado.getColumnIndexOrThrow("Usuario_ID"));
                agendamento.procedimento_id = resultado.getInt(resultado.getColumnIndexOrThrow("Procedimento_ID"));
                agendamento.dia = resultado.getString(resultado.getColumnIndexOrThrow("Dia"));
                agendamento.hora = resultado.getString(resultado.getColumnIndexOrThrow("Hora"));
                agendamento.procedimento = resultado.getString(resultado.getColumnIndexOrThrow("Procedimento"));
                agendamento.valor = resultado.getDouble(resultado.getColumnIndexOrThrow("Valor"));

                agendamentos.add(agendamento);

            } while (resultado.moveToNext());
        }
        resultado.close();
        return agendamentos;
    }

    public Agendamento buscarAgendamento(int id){

        Agendamento agendamento = new Agendamento();

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        String sql = "SELECT * FROM Agendamento WHERE ID = ?";
        Cursor resultado = conexao.rawQuery(sql, parametros);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();
            agendamento.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
            agendamento.usuario_id = resultado.getInt(resultado.getColumnIndexOrThrow("Usuario_ID"));
            agendamento.procedimento_id = resultado.getInt(resultado.getColumnIndexOrThrow("Procedimento_ID"));
            agendamento.dia = resultado.getString(resultado.getColumnIndexOrThrow("Dia"));
            agendamento.hora = resultado.getString(resultado.getColumnIndexOrThrow("Hora"));
            agendamento.procedimento = resultado.getString(resultado.getColumnIndexOrThrow("Procedimento"));
            agendamento.valor = resultado.getDouble(resultado.getColumnIndexOrThrow("Valor"));

            return agendamento;
        }
        resultado.close();
        return null;
    }

    public List<Agendamento> buscarAgendamentosUsuario(int id){

        List<Agendamento> agendamentos = new ArrayList<>();

        Cursor resultado = conexao.rawQuery("SELECT * FROM Agendamento WHERE Usuario_ID = " + id, null);

        if (resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                Agendamento agendamento = new Agendamento();
                agendamento.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
                agendamento.usuario_id = resultado.getInt(resultado.getColumnIndexOrThrow("Usuario_ID"));
                agendamento.procedimento_id = resultado.getInt(resultado.getColumnIndexOrThrow("Procedimento_ID"));
                agendamento.dia = resultado.getString(resultado.getColumnIndexOrThrow("Dia"));
                agendamento.hora = resultado.getString(resultado.getColumnIndexOrThrow("Hora"));
                agendamento.procedimento = resultado.getString(resultado.getColumnIndexOrThrow("Procedimento"));
                agendamento.valor = resultado.getDouble(resultado.getColumnIndexOrThrow("Valor"));

                agendamentos.add(agendamento);

            } while (resultado.moveToNext());
        }
        resultado.close();
        return agendamentos;
    }
}
