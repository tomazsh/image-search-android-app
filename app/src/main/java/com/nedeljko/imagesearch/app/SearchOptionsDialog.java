package com.nedeljko.imagesearch.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class SearchOptionsDialog extends DialogFragment {
    static ImageSize[] sImageSizes =  ImageSize.values();
    static ImageColor[] sImageColors = ImageColor.values();
    static ImageType[] sImageTypes = ImageType.values();

    Spinner mImageSizeSpinner;
    Spinner mImageColorSpinner;
    Spinner mImageTypeSpinner;
    EditText mSiteEditText;
    SearchOptionsDialogListener mListener;

    ImageSearchOptions mSearchOptions;

    public interface SearchOptionsDialogListener {
        void onFinishSearchOptionsDialogListener();
    }

    public SearchOptionsDialog(ImageSearchOptions searchOptions) {
        mSearchOptions = searchOptions;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ContextThemeWrapper context = new ContextThemeWrapper(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.fragment_filter_options, null);

        List<ImageSize> imageSizes = Arrays.asList(sImageSizes);
        ArrayAdapter<ImageSize> sizesAdapter = new ArrayAdapter<ImageSize>(context,
                android.R.layout.simple_spinner_item, imageSizes);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mImageSizeSpinner = (Spinner)v.findViewById(R.id.imageSizeSpinner);
        mImageSizeSpinner.setAdapter(sizesAdapter);
        mImageSizeSpinner.setSelection(imageSizes.indexOf(mSearchOptions.getImageSize()));

        List<ImageColor> imageColors = Arrays.asList(sImageColors);
        ArrayAdapter<ImageColor> colorsAdapter = new ArrayAdapter<ImageColor>(context,
                android.R.layout.simple_spinner_item, imageColors);
        colorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mImageColorSpinner = (Spinner)v.findViewById(R.id.imageColorSpinner);
        mImageColorSpinner.setAdapter(colorsAdapter);
        mImageColorSpinner.setSelection(imageColors.indexOf(mSearchOptions.getImageColor()));

        List<ImageType> imageTypes = Arrays.asList(sImageTypes);
        ArrayAdapter<ImageType> typesAdapter = new ArrayAdapter<ImageType>(context,
                android.R.layout.simple_spinner_item, Arrays.asList(sImageTypes));
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mImageTypeSpinner = (Spinner)v.findViewById(R.id.imageTypeSpinner);
        mImageTypeSpinner.setAdapter(typesAdapter);
        mImageTypeSpinner.setSelection(imageTypes.indexOf(mSearchOptions.getImageType()));

        mSiteEditText = (EditText)v.findViewById(R.id.siteEditText);
        mSiteEditText.setText(mSearchOptions.getSite());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.search_options)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ImageSize imageSize = (ImageSize) mImageSizeSpinner.getSelectedItem();
                        mSearchOptions.setImageSize(imageSize);

                        ImageColor imageColor = (ImageColor) mImageColorSpinner.getSelectedItem();
                        mSearchOptions.setImageColor(imageColor);

                        ImageType imageType = (ImageType) mImageTypeSpinner.getSelectedItem();
                        mSearchOptions.setImageType(imageType);

                        mSearchOptions.setSite(mSiteEditText.getText().toString());
                        mListener.onFinishSearchOptionsDialogListener();

                        mSearchOptions.writeDefaults();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SearchOptionsDialogListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement SearchOptionsDialogListener");
        }
    }
}
