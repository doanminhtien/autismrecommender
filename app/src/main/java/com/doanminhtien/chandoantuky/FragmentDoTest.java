package com.doanminhtien.chandoantuky;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by doanminhtien on 13/10/2017.
 */

public class FragmentDoTest extends FragmentPage {

    LinearLayout mQuestionHolder;

    static FragmentDoTest instance;

    public static FragmentDoTest newInstance() {
        return new FragmentDoTest();
    }

    public static FragmentDoTest getInstance()
    {
        if(instance!=null)
        {
            return instance;
        }else
        {
            return newInstance();
        }
    }

    public void addTest(Test test)
    {
        List<Question> questions = test.getQuestions();
        for(int i=0; i<questions.size(); i++)
        {
            addView(questions.get(i));
        }
    }


    public void addView(final Question question)
    {
        int numberOfOption = question.countOptions();
        if(numberOfOption>2)
        {
            LinearLayout.LayoutParams lpn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View v;
            LayoutInflater inflater = (LayoutInflater.from(FragmentDoTest.this.getActivity()));
            v = inflater.inflate(R.layout.item_do_test_multiple_level_question, null);

            final TextView questionTitleTextView = v.findViewById(R.id.question);
            questionTitleTextView.setText(question.getContent());
            final TextView questionOptionTextView = v.findViewById(R.id.option_description);
            SeekBar answerSeekbar = v.findViewById(R.id.answer_seekbar);
            final TextView valueTextView = v.findViewById(R.id.value);

            answerSeekbar.setMax(numberOfOption*2 - 2);

            answerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    valueTextView.setText("" + (float) (1+ 0.5 * ((float) i)));
                    question.setChosenOption((float) (1+ 0.5 * ((float) i)));
                    questionOptionTextView.setText(question.getOptions().get(i/2));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            mQuestionHolder.addView(v);
        }
        else
        {
            LinearLayout.LayoutParams lpn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this.getActivity());
            final Button[] buttons = new Button[question.getOptions().size()];
            textView.setText(question.getContent());
            List<String> options = question.getOptions();
            final int countOptions = options.size();
            textView.setLayoutParams(lpn);
            mQuestionHolder.addView(textView);
            textView.setVisibility(View.VISIBLE);
            for(int i=0; i<countOptions;i++)
            {
                final int currentI = i;
                buttons[i] = new Button(this.getActivity());
                buttons[i].setText(options.get(i));
                buttons[i].setLayoutParams(lpn);
                mQuestionHolder.addView(buttons[i]);
                buttons[i].setVisibility(View.VISIBLE);
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int j=0; j<countOptions; j++)
                        {
                            buttons[j].setEnabled(true);
                        }
                        buttons[currentI].setEnabled(false);
                        question.setChosenOption(currentI);
                    }
                });
            }
        }

    }

    private void testView()
    {
        Question question = new Question("Cau hoi 1");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);
        question = new Question("Cau hoi 2");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);
        question = new Question("Cau hoi 3");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);
        question = new Question("Cau hoi 4");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);
        question = new Question("Cau hoi 5");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);
        question = new Question("Cau hoi 6");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);
        question = new Question("Cau hoi 7");
        question.addOption("Yes");
        question.addOption("No");
        addView(question);

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_test, container, false);
        mQuestionHolder = (LinearLayout) view.findViewById(R.id.question_holder);
        final Test test = new Test();
        test.downloadTest(TestUrls.getTestCars);
        test.setOnTestResult(new Test.TestResult()
        {
            @Override
            public void onTestResult() {
                addTest(test);
            }
        });

        mQuestionHolder.invalidate();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
