package ehu.das.workit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.time.Duration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddElementoFragment} factory method to
 * create an instance of this fragment.
 */
public class AddElementoFragment extends Fragment implements IOnBackPressed {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_elemento, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText et = getActivity().findViewById(R.id.valorPeso);
        SeekBar seekBar = getActivity().findViewById(R.id.peso);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                et.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int d = Integer.parseInt(String.valueOf(et.getText()));
                seekBar.setProgress(d);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
  }


    @Override
    public boolean onBackPressed() {
        Toast.makeText(getContext(), "hola", Toast.LENGTH_LONG).show();
        Navigation.findNavController(getView()).navigate(R.id.action_addElementoFragment_to_menuFragment);
        return false;
    }
}