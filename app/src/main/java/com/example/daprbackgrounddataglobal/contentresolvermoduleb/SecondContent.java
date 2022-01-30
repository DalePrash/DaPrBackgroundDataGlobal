package com.example.daprbackgrounddataglobal.contentresolvermoduleb;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.daprbackgrounddataglobal.R;

import java.util.ArrayList;

public class SecondContent extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ContentProviderDemo";

    //private int MY_PERMISSIONS_REQUEST_READ_CONTACTS=20;

    private boolean firstTimeLoaded=false;

    private TextView textViewQueryResult;
    private Button buttonLoadData, buttonAddContact,buttonRemoveContact,buttonUpdateContact;

    private ContentResolver contentResolver;

    private EditText editTextContactName;
    private CursorLoader mContactsLoader;

    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};

    private String mSelectionCluse = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";

    private String[] mSelectionArguments = new String[]{"Ajay"};

    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resolver);

        textViewQueryResult = (TextView) findViewById(R.id.textViewQueryResult);

        editTextContactName=(EditText)findViewById(R.id.editTextContactName);

        buttonLoadData = (Button) findViewById(R.id.buttonLoadData);

        buttonAddContact=(Button)findViewById(R.id.buttonAddContact);
        buttonRemoveContact=(Button)findViewById(R.id.buttonRemoveContact);
        buttonUpdateContact=(Button)findViewById(R.id.buttonUpdateContact);



        buttonLoadData.setOnClickListener(this);

        buttonAddContact.setOnClickListener(this);
        buttonRemoveContact.setOnClickListener(this);
        buttonUpdateContact.setOnClickListener(this);


        contentResolver=getContentResolver();
loadContacts();
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                mColumnProjection,
                null,
                null,
                null);
        if(cursor!=null && cursor.getCount()>0){
            StringBuilder stringBuilderQueryResult=new StringBuilder("");
            while (cursor.moveToNext()){
                stringBuilderQueryResult.append(cursor.getString(0)+" , "+cursor.getString(1)+" , "+cursor.getString(2)+"\n");
            }
            textViewQueryResult.setText(stringBuilderQueryResult.toString());
        }else{
            textViewQueryResult.setText("No Contacts in device");
        }


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i==1){
            return  new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,mColumnProjection, null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor!=null && cursor.getCount()>0){
            StringBuilder stringBuilderQueryResult=new StringBuilder("");
            while (cursor.moveToNext()){
                stringBuilderQueryResult.append(cursor.getString(0)+" , "+cursor.getString(1)+" , "+cursor.getString(2)+" , "+cursor.getString(3)+"\n");
            }
            textViewQueryResult.setText(stringBuilderQueryResult.toString());
        }else{
            textViewQueryResult.setText("No Contacts in device");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLoadData:
                if(firstTimeLoaded==false){
                    getLoaderManager().initLoader(1, null,this);
                    firstTimeLoaded=true;
                }else{
                    getLoaderManager().restartLoader(1,null,this);
                }

                break;
            case R.id.buttonAddContact: addContact();
                break;
            case R.id.buttonRemoveContact:removeContacts();
                break;
            case R.id.buttonUpdateContact: updateContact();
                break;
            default:
                break;
        }
    }
    private void addContact() {
        ArrayList<ContentProviderOperation> cops=new ArrayList<ContentProviderOperation>();

        cops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,"accountname@gmail.com")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                .build());
        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, editTextContactName.getText().toString())
                .build());

        try{
            getContentResolver().applyBatch(ContactsContract.AUTHORITY,cops);
        }catch (Exception exception){
            Log.i(TAG,exception.getMessage());
        }
    }


    private void updateContact(){

        String[] updateValue=editTextContactName.getText().toString().split(" ");
        ContentProviderResult[] result=null;

        String targetString=null;
        String newString=null;
        if(updateValue.length==2){

            targetString=updateValue[0];
            newString=updateValue[1];

            String where= ContactsContract.RawContacts._ID + " = ? ";
            String[] params= new String[] {targetString};

            ContentResolver contentResolver=getContentResolver();
            ContentValues contentValues=new ContentValues();
            contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,newString);
            contentResolver.update(ContactsContract.RawContacts.CONTENT_URI,contentValues, where,params);
        }
    }

    private void removeContacts(){
        String whereClause= ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY+ " = '"+editTextContactName.getText().toString()+"'";
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,whereClause,null);
    }

    private void addContactsViaIntents(){
        String tempContactText=editTextContactName.getText().toString();
        if(tempContactText!=null && !tempContactText.equals("") && tempContactText.length()>0 ){
            Intent intent=new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME,tempContactText);
            startActivity(intent);
        }
    }



    private void loadContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            getLoaderManager().initLoader(1, null, this);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(SecondContent.this, new String[]{Manifest.permission.READ_CONTACTS}, 0);

             /*   Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();

              */
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 0);
            }
        }
    }
}

