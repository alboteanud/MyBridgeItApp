package com.craiovadata.mybridgeitapplication.ui.form;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.craiovadata.mybridgeitapplication.R;
import com.craiovadata.mybridgeitapplication.model.UserItem;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.YEAR_IN_MILLIS;
import static com.craiovadata.mybridgeitapplication.ui.form.DatePickerFragment.DATE_PICKER_BUNDLE_KEY;
import static com.craiovadata.mybridgeitapplication.ui.form.DatePickerFragment.DATE_PICKER_REQUEST_KEY;

public class FormFragment extends Fragment {

    private static final String TAG = FormFragment.class.getSimpleName();

    // Views
    private EditText nameEditText;
    private EditText birthEditText;
    private EditText cityEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private CountryCodePicker countryPicker;
    private Button registerButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_form, container, false);
        initViews(root);
        setupListeners();
        return root;
    }

    private void initViews(View root) {
        nameEditText = root.findViewById(R.id.name_edit_text);
        birthEditText = root.findViewById(R.id.birth_edit_text);
        cityEditText = root.findViewById(R.id.city_edit_text);
        emailEditText = root.findViewById(R.id.email_edit_text);
        passwordEditText = root.findViewById(R.id.password_edit_text);
        repeatPasswordEditText = root.findViewById(R.id.repeat_password_edit_text);
        countryPicker = root.findViewById(R.id.country_picker);
        registerButton = root.findViewById(R.id.register_button);
    }

    private void setupListeners() {

        registerButton.setOnClickListener(v -> {
            UserItem userItem = doValidateForm();
            if (userItem != null) {
                doRegisterUser(userItem);
            }
        });

        birthEditText.setOnClickListener(v -> {

            doValidateBirthDate(true);
            showDatePickerDialog(v);
        });

        // waiting for DatePicker result
        getParentFragmentManager().setFragmentResultListener(DATE_PICKER_REQUEST_KEY, this, (requestKey, bundle) -> {
            Date birthDate = (Date) bundle.getSerializable(DATE_PICKER_BUNDLE_KEY);
            Log.d(TAG, birthDate.toString());
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String textToShow = format.format(birthDate.getTime());
            birthEditText.setText(textToShow);
            birthEditText.setTag(birthDate);

            // show a Toast if date is out of limits
            // also this will show an error red sign
            doValidateBirthDate(true);
        });

        repeatPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doValidateRepeatPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void doRegisterUser(UserItem userItem) {
        Toast.makeText(getActivity(), getString(R.string.toast_user_registered), Toast.LENGTH_LONG).show();
    }

    // Text utils
    private UserItem doValidateForm() {
        String name = doValidateName();
        Date birthDate = doValidateBirthDate(false);
        String country = countryPicker.getSelectedCountryName();
        String city = doValidateCity();
        String email = doValidateEmail();
        String password = doValidatePassword();

        if (name != null
                && birthDate != null
                && country != null
                && city != null
                && email != null
                && password != null
                && doValidateRepeatPassword()) {
            return new UserItem(name, birthDate, country, city, email, password);
        }
        return null;
    }

    private String doValidateName() {
        String name = nameEditText.getText().toString();
        if (name.length() < 3) {
            nameEditText.setError(getString(R.string.error_name));
            return null;
        }
        nameEditText.setError(null);
        return name;
    }

    private Date doValidateBirthDate(boolean showToast) {
        Date birthDate = (Date) birthEditText.getTag();
        Date now = new Date();
        Date youngLimit = new Date(System.currentTimeMillis() - 18 * YEAR_IN_MILLIS);
        Date oldLimit = new Date(System.currentTimeMillis() - 150 * YEAR_IN_MILLIS);

        String errorMsg = null;
        if (birthDate == null || birthDate.after(now) || birthDate.before(oldLimit)) {
            errorMsg = getString(R.string.error_birth_incorect);
        } else if (birthDate.after(youngLimit)) {
            errorMsg = getString(R.string.error_birth_too_young);
        }

        birthEditText.setError(errorMsg);
        if (showToast && birthDate != null && errorMsg != null) {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        }

        if (errorMsg != null) return null;
        return birthDate;
    }

    private String doValidateCity() {
        String city = cityEditText.getText().toString();
        if (city.length() < 2) {
            cityEditText.setError(getString(R.string.error_city));
            return null;
        }
        cityEditText.setError(null);
        return city;
    }

    private String doValidateEmail() {
        String email = emailEditText.getText().toString();
        boolean isValid = (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isValid) {
            emailEditText.setError(getString(R.string.error_email));
            return null;
        }
        emailEditText.setError(null);
        return email;
    }

    private String doValidatePassword() {
        String password = passwordEditText.getText().toString();
        if (password.length() < 8) {
            passwordEditText.setError(getString(R.string.error_passwort_short));
            return null;
        }
        passwordEditText.setError(null);
        return password;
    }

    private boolean doValidateRepeatPassword() {
        String password = passwordEditText.getText().toString();
        String repeatPassWord = repeatPasswordEditText.getText().toString();

        if (!password.equals(repeatPassWord)) {
            repeatPasswordEditText.setError(getString(R.string.error_passwort_do_not_mach));
            return false;
        }
        repeatPasswordEditText.setError(null);
        return true;
    }

    private void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getParentFragmentManager(), "datePicker");
    }

}

