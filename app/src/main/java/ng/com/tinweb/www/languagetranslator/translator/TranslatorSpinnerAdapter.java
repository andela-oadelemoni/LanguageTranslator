package ng.com.tinweb.www.languagetranslator.translator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ng.com.tinweb.www.languagetranslator.R;

/**
 * Created by kamiye on 14/09/2016.
 */
public class TranslatorSpinnerAdapter extends ArrayAdapter<String> {

    private List<String> languages;

    public TranslatorSpinnerAdapter(Context context, int resource, List<String> languages) {
        super(context, resource, languages);
        this.languages = languages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(getContext());
        txt.setPadding(20, 20, 20, 20);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_arrow_light, 0);
        txt.setText(languages.get(position));
        return  txt;
    }
}
