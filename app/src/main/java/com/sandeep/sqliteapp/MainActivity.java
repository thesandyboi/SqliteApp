package com.sandeep.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
     DatabaseHelper myDb;
     EditText editName,editSurname,editMarks,editTextId;
     Button btnAddData;
     Button btnviewAll;
     Button btnviewUpdate;
     Button btnDelete;

     


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_Marks);
        editTextId = (EditText)findViewById(R.id.editText_Id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button)findViewById(R.id.button_update);
        btnDelete   = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();


    }

    public void AddData(){
        btnAddData.setOnClickListener(
                v -> {
                  boolean isInserted =  myDb.insertData( editName.getText().toString(),editSurname.getText().toString(),
                            editMarks.getText().toString() );

                  if(isInserted == true)
                      Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                  else
                      Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                }
        );
    }


    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Surname :"+ res.getString(2)+"\n");
                            buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }

                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());

                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Update",Toast.LENGTH_LONG).show();



                    }
                }

        );
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener(){

                    private BreakIterator editText_Id;

                    @Override
                    public void onClick(View v){
                        Integer deletedRows = myDb.deleteData(editText_Id.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();



                    }
                }
        );
    }







}