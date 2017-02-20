package uk.ac.qub.leaderelectiongame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import uk.ac.qub.leaderelectiongame.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchPaperFragment extends Fragment {

    public ResearchPaperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_paper, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Loading PDF
        PDFView pdfView = (PDFView) view.findViewById(R.id.pdfview);
        pdfView.fromAsset("research_paper.pdf")
                .pages(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();
    }
}
