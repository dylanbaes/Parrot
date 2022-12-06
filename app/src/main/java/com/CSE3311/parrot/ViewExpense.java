package com.CSE3311.parrot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.CSE3311.parrot.Models.Expense;
import com.CSE3311.parrot.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ViewExpense extends AppCompatActivity {

    private TextView categoryName;
    private TextView description;
    private TextView paymentType;
    private TextView categoryType;
    private TextView startDate;
    private TextView endDate;
    private TextView notificationType;
    private TextView notificationDate;
    private TextView cost;
    private ArrayList<Expense> expense;
    private AlertDialog.Builder editField;
    private EditText editFieldValue;
    private Button delete;
    User userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_view_expense);
        String uuid ="not set";
        editFieldValue = new EditText(ViewExpense.this);
        delete = findViewById(R.id.deletebutton);
        delete.setText("DELETE");

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            uuid = extras.getString("uuid");
            expense = (ArrayList<Expense>)extras.get("expenses");
            userInfo = (User)extras.get("userInfo");
        }

        Expense entry;

        entry = getEntry(uuid);


        categoryName = findViewById(R.id.category_name);
        description = findViewById(R.id.description);
        paymentType = findViewById(R.id.payment_amount);
        categoryType = findViewById(R.id.payment_type);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        notificationType = findViewById(R.id.notification_type);
        notificationDate = findViewById(R.id.notification_date);
        cost = findViewById(R.id.cost);

        if (entry!=null) {
            categoryName.setText(entry.getCategoryName());
            description.setText(entry.getDescription());
            paymentType.setText(entry.getPaymentType());
            categoryType.setText(entry.getCategoryType());
            startDate.setText(entry.getStartDate());
            endDate.setText((entry.getEndDate()));
            notificationType.setText(entry.getNotificationType());
            notificationDate.setText(entry.getNotificationDate());
            cost.setText(entry.getCost());
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.back_from_setting:
                        startActivity(new Intent(ViewExpense.this, MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        String finalUuid = uuid;

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExpense.this);
                builder.setCancelable(true);
                builder.setTitle("Delete Entry");
                builder.setMessage("Would you like to delete this entry?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userInfo.deleteExpense(entry);
                                startActivity(new Intent(ViewExpense.this, MainActivity.class));
                                overridePendingTransition(0,0);
                                finish();
                                return;
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
        categoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewExpense.this);
                editField.setTitle("Edit Category Name:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String category = editFieldValue.getText().toString();
                        entry.setCategoryName(category);
                        userInfo.updateExpense(entry);
                        categoryName.setText(category);
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewExpense.this);
                    }
                });
                editField.show();
            }

        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewExpense.this);
                editField.setTitle("Edit Description:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String update = editFieldValue.getText().toString();
                        entry.setDescription(update);
                        userInfo.updateExpense(entry);
                        description.setText((update));
//                        for (int i = 0; i < expense.size(); i++) {
//                            if (expense.get(i).getUuid().equals(finalUuid)) {
//                                userInfo.getExpenseLists().get(i).setCategoryName(category);
//                            }
//                        }
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewExpense.this);
                    }
                });
                editField.show();
            }

        });

        paymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewExpense.this);
                editField.setTitle("Edit Payment Type:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String update = editFieldValue.getText().toString();
                        entry.setPaymentType(update);
                        userInfo.updateExpense(entry);
                        paymentType.setText((update));
//                        for (int i = 0; i < expense.size(); i++) {
//                            if (expense.get(i).getUuid().equals(finalUuid)) {
//                                userInfo.getExpenseLists().get(i).setCategoryName(category);
//                            }
//                        }
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewExpense.this);
                    }
                });
                editField.show();
            }

        });

        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editField = new AlertDialog.Builder(ViewExpense.this);
                editField.setTitle("Edit Cost:");
                editField.setView(editFieldValue);
                editField.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String update = editFieldValue.getText().toString();
                        entry.setCost(update);
                        userInfo.updateExpense(entry);
                        cost.setText((update));
//                        for (int i = 0; i < expense.size(); i++) {
//                            if (expense.get(i).getUuid().equals(finalUuid)) {
//                                userInfo.getExpenseLists().get(i).setCategoryName(category);
//                            }
//                        }
                    }
                });
                editField.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.cancel();
                        editFieldValue = new EditText(ViewExpense.this);
                    }
                });
                editField.show();
            }

        });


    }

    private Expense getEntry(String uuid) {
        for (int i = 0; i < expense.size(); i++) {
            if (expense.get(i).getUuid().equals(uuid)) {
                return expense.get(i);
            }
        }
        return null;
    }


}
