package tk.yurkiv.recipes.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.angmarch.views.NiceSpinner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.yurkiv.recipes.R;
import tk.yurkiv.recipes.model.Category;
import tk.yurkiv.recipes.util.Utils;

/**
 * Created by yurkiv on 01.09.2015.
 */
public class FilterFragment extends DialogFragment {

    @InjectView(R.id.nsCourse) NiceSpinner nsCourse;
    @InjectView(R.id.nsCuisine) NiceSpinner nsCuisine;
    @InjectView(R.id.nsHoliday) NiceSpinner nsHoliday;
    @InjectView(R.id.nsDiet) NiceSpinner nsDiet;
    @InjectView(R.id.nsAllergy) NiceSpinner nsAllergy;
    @InjectView(R.id.tvTotalTime) TextView tvTotalTime;
    @InjectView(R.id.dsbTotalTime) DiscreteSeekBar dsbTotalTime;
    @InjectView(R.id.tvEnergy) TextView tvEnergy;
    @InjectView(R.id.dsbEnergy) DiscreteSeekBar dsbEnergy;

    private List<Category> courseList;
    private List<Category> cuisineList;
    private List<Category> holidayList;
    private List<Category> dietList;
    private List<Category> allergyList;

    private HashMap<String, String> filters;
    private FilterCallback filterCallback;


    private final MaterialDialog.ButtonCallback buttonCallback=new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog dialog) {
            dialog.dismiss();

            if (nsCourse.getSelectedIndex()!=0){
                filters.put(HomeFragment.COURSE_KEY, courseList.get(nsCourse.getSelectedIndex()-1).getSearchValue());
            }
            if (nsCuisine.getSelectedIndex()!=0){
                filters.put(HomeFragment.CUISINE_KEY, cuisineList.get(nsCuisine.getSelectedIndex()-1).getSearchValue());
            }
            if (nsHoliday.getSelectedIndex()!=0){
                filters.put(HomeFragment.HOLIDAY_KEY, holidayList.get(nsHoliday.getSelectedIndex()-1).getSearchValue());
            }
            if (nsDiet.getSelectedIndex()!=0){
                filters.put(HomeFragment.DIET_KEY, dietList.get(nsDiet.getSelectedIndex()-1).getSearchValue());
            }
            if (nsAllergy.getSelectedIndex()!=0){
                filters.put(HomeFragment.ALLERGY_KEY, allergyList.get(nsAllergy.getSelectedIndex()-1).getSearchValue());
            }

            filters.put(HomeFragment.MAX_TOTAL_TIME, String.valueOf(dsbTotalTime.getProgress()*60*60));
            filters.put(HomeFragment.MAX_ENERGY, String.valueOf(dsbEnergy.getProgress()*100));

            filterCallback.onFilter(filters);
        }

        @Override
        public void onNegative(MaterialDialog dialog) {
            dialog.dismiss();
        }
    };

    public interface FilterCallback {
        void onFilter(HashMap<String, String> filters);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            filterCallback = (FilterCallback) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }

        filters=new HashMap<>();
        filters.put(HomeFragment.COURSE_KEY, null);
        filters.put(HomeFragment.CUISINE_KEY, null);
        filters.put(HomeFragment.HOLIDAY_KEY, null);
        filters.put(HomeFragment.DIET_KEY, null);
        filters.put(HomeFragment.ALLERGY_KEY, null);
        filters.put(HomeFragment.MAX_TOTAL_TIME, null);
        filters.put(HomeFragment.MAX_ENERGY, null);

        courseList=Utils.getCategoryRes(getActivity(), "course.json");
        cuisineList=Utils.getCategoryRes(getActivity(), "cuisine.json");
        holidayList=Utils.getCategoryRes(getActivity(), "holiday.json");
        dietList=Utils.getCategoryRes(getActivity(), "diet.json");
        allergyList=Utils.getCategoryRes(getActivity(), "allergy.json");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_filter, true)
                .callback(buttonCallback)
                .autoDismiss(false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .build();

        ButterKnife.inject(this, dialog);

        tvTotalTime.setText(getString(R.string.total_time) + "12 hrs");
        tvEnergy.setText(getString(R.string.calories)+"1000");

        nsCourse.attachDataSource(getCategoryNameList(courseList, "course.json"));
        nsCuisine.attachDataSource(getCategoryNameList(cuisineList, "cuisine.json"));
        nsHoliday.attachDataSource(getCategoryNameList(holidayList, "holiday.json"));
        nsDiet.attachDataSource(getCategoryNameList(dietList, "diet.json"));
        nsAllergy.attachDataSource(getCategoryNameList(allergyList, "allergy.json"));

        dsbTotalTime.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
                tvTotalTime.setText(getString(R.string.total_time) + i + getString(R.string.hrs));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }
        });

        dsbEnergy.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int i) {
                return i * 100;
            }
        });

        dsbEnergy.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
                tvEnergy.setText(getString(R.string.calories) + i*100);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }
        });

        return dialog;
    }

    private List<String> getCategoryNameList(List<Category> categories, String name){
        List<String> dataset = new LinkedList<>();
        dataset.add(getString(R.string.all));
        for (Category category : categories){
            switch (name){
                case "diet.json":
                    dataset.add(category.getLongDescription());
                    break;
                case "allergy.json":
                    dataset.add(category.getLongDescription());
                    break;
                default:
                    dataset.add(category.getName());
            }
        }
        return dataset;
    }

}
