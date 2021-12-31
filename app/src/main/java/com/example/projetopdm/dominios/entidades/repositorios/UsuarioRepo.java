package com.example.projetopdm.dominios.entidades.repositorios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projetopdm.dominios.entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepo {

    public static SQLiteDatabase conexao;

    public UsuarioRepo usuarioRepo;

    public UsuarioRepo(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

   public void inserir(Usuarios usuarios){

       ContentValues contentValues = new ContentValues();
       contentValues.put("Nome", usuarios.nome);
       contentValues.put("Sobrenome", usuarios.sobrenome);
       contentValues.put("Telefone", usuarios.telefone);
       contentValues.put("Cidade", usuarios.cidade);
       contentValues.put("CPF", usuarios.CPF);
       contentValues.put("RG", usuarios.RG);
       contentValues.put("Nascimento", usuarios.nascimento);
       contentValues.put("Email", usuarios.email);
       contentValues.put("Senha", usuarios.senha);
       conexao.insertOrThrow("Usuarios", "ID", contentValues);
   }

   public void excluir(int id){

       String[] parametros = new String[1];
       parametros[0] = String.valueOf(id);

       conexao.delete("Usuarios", "ID = ?", parametros);
   }

   public void alterar(Usuarios usuarios){

       ContentValues contentValues = new ContentValues();
       contentValues.put("Nome", usuarios.nome);
       contentValues.put("Sobrenome", usuarios.sobrenome);
       contentValues.put("Telefone", usuarios.telefone);
       contentValues.put("Cidade", usuarios.cidade);
       contentValues.put("CPF", usuarios.CPF);
       contentValues.put("RG", usuarios.RG);
       contentValues.put("Nascimento", usuarios.nascimento);
       contentValues.put("Email", usuarios.email);
       contentValues.put("Senha", usuarios.senha);

       String[] parametros = new String[1];
       parametros[0] = String.valueOf(usuarios.ID);

       conexao.update("Usuarios", contentValues, "ID = ?", parametros);
   }

   public List<Usuarios> buscarUsuarios() {

        List<Usuarios> usuarios = new ArrayList<Usuarios>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * ");
        //sql.append("SELECT Nome, Sobrenome, Telefone, Cidade, CPF, RG, Nascimento, Email, Senha ");
        sql.append("FROM Usuarios ");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            do {
                Usuarios usuario = new Usuarios();

                //usuario.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
                usuario.nome = resultado.getString(resultado.getColumnIndexOrThrow("Nome"));
                usuario.sobrenome = resultado.getString(resultado.getColumnIndexOrThrow("Sobrenome"));
                usuario.telefone = resultado.getString(resultado.getColumnIndexOrThrow("Telefone"));
                usuario.cidade = resultado.getString(resultado.getColumnIndexOrThrow("Cidade"));
                usuario.RG = resultado.getString(resultado.getColumnIndexOrThrow("RG"));
                usuario.CPF = resultado.getString(resultado.getColumnIndexOrThrow("CPF"));
                usuario.nascimento = resultado.getString(resultado.getColumnIndexOrThrow("Nascimento"));
                usuario.email = resultado.getString(resultado.getColumnIndexOrThrow("Email"));
                usuario.senha = resultado.getString(resultado.getColumnIndexOrThrow("Senha"));

                usuarios.add(usuario);

            } while (resultado.moveToNext());

        }
       return null;
   }

   public Usuarios buscarUsuario(String email){

       StringBuilder sql = new StringBuilder();
       sql.append("SELECT * FROM Usuarios WHERE Email=? ");

       String[] parametros = new String[]{email};

       Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

       Usuarios usuarios = new Usuarios();

       if (resultado.moveToNext()) {
           usuarios.nome = resultado.getString(resultado.getColumnIndexOrThrow("Nome"));
           usuarios.sobrenome = resultado.getString(resultado.getColumnIndexOrThrow("Sobrenome"));
           usuarios.telefone = resultado.getString(resultado.getColumnIndexOrThrow("Telefone"));
           usuarios.cidade = resultado.getString(resultado.getColumnIndexOrThrow("Cidade"));
           usuarios.RG = resultado.getString(resultado.getColumnIndexOrThrow("RG"));
           usuarios.CPF = resultado.getString(resultado.getColumnIndexOrThrow("CPF"));
           usuarios.nascimento = resultado.getString(resultado.getColumnIndexOrThrow("Nascimento"));
           usuarios.email = resultado.getString(resultado.getColumnIndexOrThrow("Email"));
           usuarios.senha = resultado.getString(resultado.getColumnIndexOrThrow("Senha"));
           usuarios.ID = resultado.getInt(resultado.getColumnIndexOrThrow("ID"));
       }

       return usuarios;
   }

    public boolean validaSenha(String senha, String email) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Usuarios WHERE Senha=? AND Email=?");

        String[] parametros = new String[]{senha, email};

        Cursor resultado = conexao.rawQuery(sql.toString(),parametros);

        // Retorna se o cursor tem 1 resultado com o email e senha informados
        return resultado != null && resultado.getCount() > 0;

    }

}
