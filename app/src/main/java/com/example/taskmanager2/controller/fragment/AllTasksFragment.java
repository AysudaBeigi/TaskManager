package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.taskmanager2.controller.fragment.TaskListFragment.REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT;
import static com.example.taskmanager2.controller.fragment.TaskListFragment.TAG_EDITABLE_DETAIL_FRAGMENT;


public class AllTasksFragment extends Fragment {
    private RecyclerView mAllTaskRecyclerView;
    private AllTasksAdapter mAllTasksAdapter;
    private TaskDBRepository mTaskDBRepository;
    private LinearLayout mLinearLayout;


    public AllTasksFragment() {
        // Required empty public constructor
    }


    public static AllTasksFragment newInstance() {
        Log.d("TAG", "AllTasksFragment newInstance");
        AllTasksFragment fragment = new AllTasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "AllTasksFragment onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TAG", "AllTasksFragment onCreateView");

        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);

        findView(view);

        initView();

        updateList();

        return view;
    }

    private void updateList() {

        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        List<Task> tasks = mTaskDBRepository.getTasks();

        if (mAllTasksAdapter != null) {

            mAllTasksAdapter.setTasks(tasks);
            mAllTasksAdapter.notifyDataSetChanged();
        } else {
            mAllTasksAdapter = new AllTasksAdapter(tasks);
            mAllTaskRecyclerView.setAdapter(mAllTasksAdapter);
        }
    }

    private void initView() {
        mAllTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void findView(View view) {
        mAllTaskRecyclerView = view.findViewById(R.id.recycler_view_all_tasks);
        mLinearLayout = view.findViewById(R.id.linear_layout_all_tasks);
    }


    public class AllTasksAdapter extends RecyclerView.Adapter<AllTasksViewHolder> {
        private List<Task> mTasks;

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public List<Task> getTasks() {
            return mTasks;
        }

        public AllTasksAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public AllTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View itemView = layoutInflater.inflate(R.layout.task_item_view, parent, false);

            return new AllTasksViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AllTasksViewHolder holder, int position) {
            holder.bindView(mTasks.get(position));

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }


    public class AllTasksViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView mTextViewTitle;
        private MaterialTextView mTextViewDate;
        private MaterialTextView mTextViewFirstChar;
        private Task mTask;

        public AllTasksViewHolder(@NonNull View itemView) {
            super(itemView);

            findViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    EditableDetailFragment editableDetailFragment =
                            EditableDetailFragment.newInstance(mTask);
                    editableDetailFragment.setTargetFragment(
                            AllTasksFragment.this, REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT);
                    editableDetailFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_EDITABLE_DETAIL_FRAGMENT);

                }
            });


        }

        public void bindView(Task task) {
            mTask = task;
            if (task != null) {
                mTextViewTitle.setText(task.getTitle());
                mTextViewDate.setText(getStringFormatDate(task.getDate()));
                if (!task.getTitle().isEmpty()) {

                    mTextViewFirstChar.setText(String.valueOf(task.getTitle().charAt(0)));
                }
            }else {
                generateSnackbar(mLinearLayout,R.string.snackbar_not_exits_any_task);
            }


        }

        private void findViews(@NonNull View itemView) {
            mTextViewTitle = itemView.findViewById(R.id.text_view_task_title);
            mTextViewDate = itemView.findViewById(R.id.text_view_date);
            mTextViewFirstChar = itemView.findViewById(R.id.text_view_first_char);
        }

    }

    /************************* GET STRING FORMAT DATE ******************/
    private String getStringFormatDate(Date date) {
        return new SimpleDateFormat("yyy/MM/dd  " + "HH:mm:ss").format(date);
    }


    /******************** ON ACTIVITY RESULT ****************/

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT) {

            Task editedTask = (Task) data.getSerializableExtra(EditableDetailFragment.EXTRA_TASK);

            updateList();
        }

    }

    /******************** GENERATE SNACK BAR *********************/
    private void generateSnackbar(View view, int stringId) {
        Snackbar snackbar = Snackbar
                .make(view,
                        stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    @Override
    public void onPause() {

        super.onPause();
        updateList();
    }

    @Override
    public void onResume() {

        super.onResume();
        updateList();
    }


}