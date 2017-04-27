package uk.ac.qub.leaderelectiongame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.activity.MainActivity;


/**
 * Fragment responsible for displaying research paper.
 */
public class ResearchPaperFragment extends Fragment {

    /**
     * Required empty public constructor.
     */
    public ResearchPaperFragment() {
    }

    /**
     * Method to create and return the view hierarchy associated with the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_research_paper, container, false);
        MainActivity parentActivity = (MainActivity) getActivity();
        if (parentActivity != null) {
            parentActivity.getResideMenu().addIgnoredView(v.findViewById(R.id.llResearchPaper));
        }   //if
        return v;
    }

    /**
     * Method to load research paper PDF.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PDFView pdfView = (PDFView) view.findViewById(R.id.pdfView);
        pdfView.fromAsset("research_paper.pdf")
                .pages(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .defaultPage(1)
                .enableSwipe(true)
                .enableAnnotationRendering(false)
                .enableAntialiasing(true)
                .load();
    }
}
