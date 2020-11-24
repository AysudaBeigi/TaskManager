package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager2.R;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskSate;
import com.example.taskmanager2.repository.UserDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class TaskListFragment extends Fragment {

    public static final String ARGS_USERNAME = "username";
    public static final String ARGS_STATE = "state";
    public static final String TAG_EDITABLE_DETAIL_FRAGMENT = "tagEditableDetailFragment";

    public static final int REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT = 1;
    public static final int REQUEST_CODE_ADD_TASK_FRAGMENT = 2;
    public static final String TAG_ADD_TASK_FRAGMENT = "tagAddTaskFragment";

    private String mUsername;
    private TaskSate mState;

    private RecyclerView mRecyclerView;
    private UserDBRepository mUserDBRepository;
    private FloatingActionButton mButtonAddTask;
    private ShapeableImageView mImgEmptyAdd;
    private MaterialTextView mTextViewEmptyAdd;

    private TaskListAdapter mTaskListAdapter;

    private Task mTask;

    /*********************** CONSTRUCTOR *********************/
    public TaskListFragment() {
        // Required empty public constructor
    }

    /********************* NEW INSTANCE ****************/
    public static TaskListFragment newInstance(String username, TaskSate taskSate) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_USERNAME, username);
        args.putSerializable(ARGS_STATE, taskSate);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************** ON CREATE ************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUsername = getArguments().getString(ARGS_USERNAME);
        mState = (TaskSate) getArguments().getSerializable(ARGS_STATE);

    }

    /************************ ON CREATE VIEW **************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        initView();
        setListener();

        return view;

    }


    /********************************** FIND VIEWS ******************************/

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
        mImgEmptyAdd = view.findViewById(R.id.img_view_empty_add);
        mTextViewEmptyAdd = view.findViewById(R.id.text_view_description);
        mButtonAddTask = view.findViewById(R.id.btn_add);
    }

    /*******************  INTI VIEW ********************************/
    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateList();

    }

    /******************* SET LISTENER ********************/
    private void setListener() {
        mButtonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskFragment addTaskFragment =
                        AddTaskFragment.newInstance(mUsername, mState);
                addTaskFragment.setTargetFragment(
                        TaskListFragment.this, REQUEST_CODE_ADD_TASK_FRAGMENT);
                addTaskFragment.show(getFragmentManager(), TAG_ADD_TASK_FRAGMENT);
            }
        });
    }

    /************************* UPDATE LIST *********************/
    public void updateList() {
        mUserDBRepository = UserDBRepository.getInstance();
        List<Task> userTasks = mUserDBRepository.getTasks(mUsername, mState);
        if (userTasks.size() != 0 && userTasks != null) {
            goneEmptyAddViews();

            if (mTaskListAdapter != null) {
                mTaskListAdapter.setTasks(userTasks);
                mTaskListAdapter.notifyDataSetChanged();
            } else {
                mTaskListAdapter = new TaskListAdapter(userTasks);
                mRecyclerView.setAdapter(mTaskListAdapter);
            }


        } else {
            visibleEmptyAddViews();
        }
    }

    /********************** VISIBLE EMPTY ADD VIEWS ******************/
    private void visibleEmptyAddViews() {
        mImgEmptyAdd.setVisibility(View.VISIBLE);
        mTextViewEmptyAdd.setVisibility(View.VISIBLE);
    }

    /*************** GONE  EMPTY ADD VIEWS *******************/
    private void goneEmptyAddViews() {
        mImgEmptyAdd.setVisibility(View.GONE);
        mTextViewEmptyAdd.setVisibility(View.GONE);
    }

    /**************************** TASK LIST ADAPTER *********************************/
    public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        List<Task> mTasks;

        public TaskListAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public List<Task> getTasks() {
            return mTasks;
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View itemView = layoutInflater.inflate(R.layout.task_item_view, parent, false);

            return new TaskViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindView(task);

        }

    }

    /********************************** TASK VIEW HOLDER **********************************/

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView mTextViewTitle;
        private MaterialTextView mTextViewDate;
        private MaterialTextView mTextViewFirstChar;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditableDetailFragment editableDetailFragment =
                            EditableDetailFragment.newInstance(mTask);
                    editableDetailFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT);
                    editableDetailFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_EDITABLE_DETAIL_FRAGMENT);

                }
            });


        }

        public void bindView(Task task) {
            mTextViewTitle.setText(task.getTitle());
            mTextViewDate.setText(task.getDate().toString());
            mTextViewFirstChar.setText(String.valueOf(task.getTitle().charAt(0)));
        }

        private void findViews(@NonNull View itemView) {
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewDate = itemView.findViewById(R.id.text_view_description);
            mTextViewFirstChar = itemView.findViewById(R.id.text_view_first_char);
        }
    }

    /******************** ON ACTIVITY RESULT ****************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        switch (requestCode) {
            case REQUEST_CODE_ADD_TASK_FRAGMENT:
                mTask= (Task) data.getSerializableExtra(AddTaskFragment.EXTRA_TASK);
                updateList();

                break;
            case REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT:
                mTask= (Task) data.getSerializableExtra(EditableDetailFragment.EXTRA_TASK);

                updateList();
                break;
        }


    }
}
