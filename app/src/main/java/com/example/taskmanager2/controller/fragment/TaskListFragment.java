package com.example.taskmanager2.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import com.example.taskmanager2.R;
import com.example.taskmanager2.controller.activity.SignInActivity;
import com.example.taskmanager2.model.Task;
import com.example.taskmanager2.model.TaskState;
import com.example.taskmanager2.repository.TaskDBRepository;
import com.example.taskmanager2.repository.UserDBRepository;
import com.example.taskmanager2.utils.Format;
import com.example.taskmanager2.utils.PictureUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TaskListFragment extends Fragment {

    public static final String ARGS_USERNAME = "username";
    public static final String ARGS_STATE = "state";
    public static final String TAG_EDITABLE_DETAIL_FRAGMENT = "tagEditableDetailFragment";
    public static final String TAG_ADD_TASK_FRAGMENT = "tagAddTaskFragment";

    public static final int REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT = 1;
    public static final int REQUEST_CODE_ADD_TASK_FRAGMENT = 2;
    public static final int REQUEST_CODE_TAKE_PICTURE = 3;
    public static final String AUTHORITY = "com.example.taskmanager2.fileprovider";

    private String mUsername;
    private TaskState mState;

    private RecyclerView mRecyclerView;
    private UserDBRepository mUserDBRepository;
    private TaskDBRepository mTaskDBRepository;
    private ShapeableImageView mButtonAddTask;
    private ShapeableImageView mImgEmptyAdd;
    private MaterialTextView mTextViewEmptyAdd;

    private TaskListAdapter mTaskListAdapter;

    private Task mTask;


    private File mPhotoFile;
    private ShapeableImageView mImageViewTaskIcon;
    private boolean mIsPhotoTaken = false;

    private Task mItemTask;


    /*********************** CONSTRUCTOR *********************/
    public TaskListFragment() {
        // Required empty public constructor
    }

    /********************* NEW INSTANCE ****************/
    public static TaskListFragment newInstance(String username, TaskState taskState) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_USERNAME, username);
        args.putSerializable(ARGS_STATE, taskState);
        fragment.setArguments(args);
        return fragment;
    }

    /**************************** ON CREATE ************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mUsername = getArguments().getString(ARGS_USERNAME);
        mState = (TaskState) getArguments().getSerializable(ARGS_STATE);
        setHasOptionsMenu(true);
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        Log.d("TAG", "TLF on create  ");


    }

    /************************ ON CREATE VIEW **************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TAG", "TLF on create View ");
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        initView();
        setListener();

        return view;

    }


    /********************************** FIND VIEWS ******************************/

    private void findViews(View view) {
        Log.d("TAG", "TLF find View ");

        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
        mImgEmptyAdd = view.findViewById(R.id.img_view_empty_add);
        mTextViewEmptyAdd = view.findViewById(R.id.text_view_empty_add);
        mButtonAddTask = view.findViewById(R.id.imge_view_floating_add);
    }

    /*******************  INTI VIEW ********************************/
    private void initView() {
        Log.d("TAG", "TLF init view ");

        mUserDBRepository = UserDBRepository.getInstance(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateList();

    }

    /************************* UPDATE LIST *********************/
    public void updateList() {
        if (mUserDBRepository != null) {
            Log.d("TAG", "update List TLF");

            mUserDBRepository = UserDBRepository.getInstance(getActivity());
            List<Task> userTasks = mUserDBRepository.getTasks(mUsername, mState);
            Log.d("TAG", "size of tasks is " + userTasks.size());
            goneEmptyAddViews();

            if (mTaskListAdapter != null) {
                Log.d("TAG", "update List taskAdapter is not null");

                mTaskListAdapter.setTasks(userTasks);
                mTaskListAdapter.notifyDataSetChanged();
            } else {
                Log.d("TAG", "update List taskAdapter is null");

                mTaskListAdapter = new TaskListAdapter(userTasks);
                mRecyclerView.setAdapter(mTaskListAdapter);
            }

            if (userTasks.size() == 0 || userTasks == null) {
                visibleEmptyAddViews();
            }
        }
    }

    /******************* SET LISTENER ********************/
    private void setListener() {
        Log.d("TAG", "TLF listener AddTask ");

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
    public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> implements Filterable {

        List<Task> mTasks;
        List<Task> mFilteredTaskList;


        public TaskListAdapter(List<Task> tasks) {
            Log.d("TAG", "TLF TaskListAdapter ");
            mTasks = tasks;

        }

        public void setTasks(List<Task> tasks) {
            Log.d("TAG", " tASK ADAPTER setTask");

            mTasks = tasks;
            mFilteredTaskList = new ArrayList<>(tasks);

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
            Log.d("TAG", "TLF onCreateViewHolder ");

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View itemView = layoutInflater.inflate(R.layout.task_item_view, parent, false);

            return new TaskViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Log.d("TAG", "TLF onBindViewHolder ");

            Task task = mTasks.get(position);
            Log.d("TAG", "task.getDate is :" + task.getDate().toString());

            holder.bindView(task);

        }

        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence searchedChars) {
                    List<Task> filteredList = new ArrayList<>();

                    if (searchedChars == null || searchedChars.toString().isEmpty()) {
                        filteredList.addAll(mFilteredTaskList);
                    } else {
                        for (Task task : mFilteredTaskList) {
                            if (getCondition(searchedChars, task)) {
                                filteredList.add(task);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;

                    return results;
                }

                private boolean getCondition(CharSequence charSequence, Task task) {
                    return task.getDate().toString().toLowerCase().trim()
                            .contains(charSequence.toString().toLowerCase().trim()) ||
                            task.getTitle().toLowerCase().trim()
                                    .contains(charSequence.toString().toLowerCase().trim()) ||
                            task.getDescription().toLowerCase().trim()
                                    .contains(charSequence.toString().toLowerCase().trim()) ||
                            task.getSate().toString().trim()
                                    .contains(charSequence.toString().toLowerCase().trim());
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (mTasks != null)
                        mTasks.clear();
                    if (filterResults.values != null)
                        mTasks.addAll((Collection<? extends Task>) filterResults.values);
                    notifyDataSetChanged();
                }

            };

        }
    }

    /********************************** TASK VIEW HOLDER **********************************/

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView mTextViewTitle;
        private MaterialTextView mTextViewDate;
        private MaterialTextView mTextViewFirstChar;
        private ShapeableImageView mImageViewShare;
        private Task mTask;

        public TaskViewHolder(@NonNull View itemView) {

            super(itemView);
            Log.d("TAG", "TLF TaskViewHolder ");
            findViews(itemView);
            setTaskItemListeners(itemView);


        }

        private void setTaskItemListeners(@NonNull View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("TAG", "TLF itemView.setOnClickListener ");
                    EditableDetailFragment editableDetailFragment =
                            EditableDetailFragment.newInstance(mTask);
                    editableDetailFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT);
                    editableDetailFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_EDITABLE_DETAIL_FRAGMENT);

                }
            });
            mImageViewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareTaskReport();
                }
            });
            mImageViewTaskIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "icon listener ");
                    takePhoto();
                }

                private void takePhoto() {
                    Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Intent takePictureIntent = Intent.createChooser(imageCaptureIntent, "take photo");
                    if (takePictureIntent.
                            resolveActivity(getActivity().getPackageManager()) != null) {
                        mPhotoFile = null;
                        try {
                            mPhotoFile = mTaskDBRepository.getPhotoFile(mTask);
                            String path = mPhotoFile.getAbsolutePath();
                            Uri photoUri = getUriForPhotoFile();
                            Log.d("TAG", "photoUri" + photoUri.toString());
                            grantWriteUriDestinationActivities(takePictureIntent, photoUri);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);

                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }

            });
        }

        private void grantWriteUriDestinationActivities(Intent takePictureIntent, Uri photoUri) {
            List<ResolveInfo> activities = getActivity()
                    .getPackageManager().
                            queryIntentActivities(
                                    takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo activity : activities) {
                getActivity().grantUriPermission(
                        activity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            }
        }

        private void shareTaskReport() {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getTaskReport());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, getString(R.string.send_task));
            if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        }

        private String getTaskReport() {
            String title = mTask.getTitle();
            String description = mTask.getDescription();
            String dateStr = Format.getStringFormatDateAndTime(mTask.getDate(), mTask.getTime());
            String stateStr = mTask.getSate().toString();
            String report = getString(R.string.task_report, title, description, dateStr, stateStr);
            return report;
        }

        public void bindView(Task task) {
            Log.d("TAG", "TLF bindView ");

            mItemTask = task;
            mTask = task;
            mTextViewTitle.setText(task.getTitle());
            mTextViewDate.setText(Format.getStringFormatDateAndTime(task.getDate(), task.getTime()));
            if (!task.getTitle().isEmpty()) {
                mTextViewFirstChar.setText(String.valueOf(task.getTitle().charAt(0)));

            }
            updateIconPhoto(task);


        }

        private void findViews(@NonNull View itemView) {

            mTextViewTitle = itemView.findViewById(R.id.text_view_task_title);
            mTextViewDate = itemView.findViewById(R.id.text_view_date);
            mTextViewFirstChar = itemView.findViewById(R.id.text_view_first_char);
            mImageViewShare = itemView.findViewById(R.id.img_view_share);
            mImageViewTaskIcon = itemView.findViewById(R.id.img_view_icon);
        }
    }

    private Uri getUriForPhotoFile() {

        return FileProvider.getUriForFile(getActivity(), AUTHORITY, mPhotoFile);
    }

    private void updateIconPhoto(Task task) {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            Log.d("TAG", "mPhotoFile is null");

        } else {

            Bitmap bitmap = PictureUtils.getScaledBitmap(task.getPhotoAddress(), getActivity());
            mImageViewTaskIcon.setImageBitmap(bitmap);

        }
    }


    /******************** ON ACTIVITY RESULT ****************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TAG", "TaskListF == onActivityResult ");

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        switch (requestCode) {
            case REQUEST_CODE_ADD_TASK_FRAGMENT:
                Log.d("TAG", "TaskListF == onActivityResult == requestCode AddTask");
                mTask = (Task) data.getSerializableExtra(AddTaskFragment.EXTRA_TASK);
                Log.d("TAG", "added task title is :" + mTask.getTitle());
                Log.d("TAG", " === mTask>date is :"
                        + mTask.getDate().toString());

                updateList();

                break;
            case REQUEST_CODE_EDITABLE_DETAIL_FRAGMENT:
                Log.d("TAG", "TaskListF == onActivityResult == requestCode EditTask");

                mTask = (Task) data.getSerializableExtra(EditableDetailFragment.EXTRA_TASK);
                Log.d("TAG", "added task title is :" + mTask.getTitle());

                Log.d("TAG", " === mTask>date is :"
                        + mTask.getDate().toString());

                updateList();
                break;
            case REQUEST_CODE_TAKE_PICTURE:
                Log.d("TAG", "take Picture ");
                mItemTask.setPhotoAddress(mPhotoFile.getAbsolutePath());
                mTaskDBRepository.updateTask(mItemTask);
                revokeWriteUriPermission();
                updateList();

                break;
        }


    }

    /*************** REVOKE WRITE URI PERMISSION *****************/
    private void revokeWriteUriPermission() {
        Log.d("TAG", "revokeWriteUriPermission");
        Uri photoUri = getUriForPhotoFile();
        getActivity().revokeUriPermission(photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }


    /****************** ON CREATE OPTION MENU ********************/
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTaskListAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    /**************** ON OPTION MENU SELECTED ****************/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                buildLogoutAlertDialog();
                return true;
            case R.id.menu_item_delete_all_tasks:
                buildDeleteUserTasksAlertDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /*************** BUILD LOGOUT USER ALERT DIALOG *****************/


    private void buildLogoutAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = SignInActivity.newIntent(getActivity());
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*************** BUILD DELETE USER TASKS ALERT DIALOG *****************/
    private void buildDeleteUserTasksAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure to delete all tasks in this state ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteUserTasks();
                        updateList();
                    }
                })
                .setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /********************** DELETE USER TASKS *******************/
    private void deleteUserTasks() {
        List<Task> userTasks = mUserDBRepository.getTasks(mUsername, mState);
        if (userTasks != null) {

            for (int j = 0; j < userTasks.size(); j++) {

                mTaskDBRepository.deleteTask(userTasks.get(j));

            }
        }

    }


    @Override
    public void onPause() {
        Log.d("TAG", "TLF  onPause");

        super.onPause();
        updateList();
    }

    @Override
    public void onResume() {
        Log.d("TAG", "TLF   onResume  ");

        super.onResume();
        updateList();
    }


}
