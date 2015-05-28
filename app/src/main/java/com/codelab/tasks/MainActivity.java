package com.codelab.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codelab.tasks.data.TaskBag;
import com.codelab.tasks.data.TaskBagFactory;
import com.codelab.tasks.ui.DividerItemDecoration;
import com.codelab.tasks.ui.FloatingActionButton;
import com.codelab.tasks.ui.TasksAdapter;

public class MainActivity extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TasksAdapter mAdapter;
    private TaskBag mTasBag;
    private TextView mtvNoTasks;
    private ActionMode actionMode;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        mtvNoTasks = (TextView) findViewById(R.id.tvNoTasks);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        fab1.setOnClickListener(this);

        mTasBag = TaskBagFactory.getTaskBag();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        mSwipeRefreshLayout.setRefreshing(true);
        backgroundPullFromRemote();

        mAdapter = new TasksAdapter(this, mTasBag.getTasks(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_1)
            openDialogAddTask();
        else
        {
            if (actionMode == null) {
                actionMode = startActionMode(mActionModeCallback);
            }

            TasksAdapter.ViewHolder holder = (TasksAdapter.ViewHolder) v.getTag();
            myToggleSelection(holder.getPosition());
        }

    }

    private void updateUI() {
        mAdapter.notifyDataSetChanged();

        if(mTasBag.getTasks().size() > 0)
        {
            mtvNoTasks.setVisibility(View.GONE);
        }
        else
        {
            mtvNoTasks.setVisibility(View.VISIBLE);
        }
    }

    private void addTask(String input)
    {
        mTasBag.addAsTask(input);
        backgroundPushToRemote();
    }

    private synchronized void backgroundPullFromRemote() {
        new AsyncTask<Void, Void, Boolean>() {
            String message;

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Log.d(TAG, "start taskBag.pullFromRemote");

                    mTasBag.pullFromRemote();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    message = e.getMessage();
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d(TAG, "post taskBag.pullFromRemote");

                mSwipeRefreshLayout.setRefreshing(false);

                if (result) {
                    Log.d(TAG, "taskBag.pullFromRemote done");
                    updateUI();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }

                super.onPostExecute(result);
            }
        }.execute();
    }

    private synchronized void backgroundPushToRemote()
    {
        updateUI();

        new AsyncTask<Void, Void, Boolean>() {
            String message;

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Log.d(TAG, "start taskBag.pushFromRemote");

                    mTasBag.pushToRemote();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    message = e.getMessage();
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d(TAG, "post taskBag.pushFromRemote");

                mSwipeRefreshLayout.setRefreshing(false);

                if (result) {
                    Log.d(TAG, "taskBag.pushFromRemote done");
                    updateUI();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }

                super.onPostExecute(result);
            }
        }.execute();
    }

    private void openDialogAddTask()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogLight);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.task_dialog, null);
        final EditText etTask = (EditText) view.findViewById(R.id.etTask);
        builder1.setView(view);

        builder1.setPositiveButton(R.string.text_button_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String input = etTask.getText().toString();
                if(!input.isEmpty())
                    addTask(input);
            }
        });

        builder1.setNegativeButton(R.string.text_button_cancel,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alert11 = builder1.create();

        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etTask, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        alert11.show();
    }


    private  ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.row_selection, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.done:
                    mTasBag.alterCompleteItems(mAdapter.getSelectedItems());
                    backgroundPushToRemote();
                    mode.finish();
                    return true;
                case R.id.remove:
                    mTasBag.removeSelectedItems(mAdapter.getSelectedItems());
                    backgroundPushToRemote();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            mAdapter.clearSelections();
        }
    };


    private void myToggleSelection(int idx) {
        mAdapter.toggleSelection(idx);
        String title = getString(R.string.selected_count, mAdapter.getSelectedItemCount());
        actionMode.setTitle(title);
    }

    @Override
    public void onRefresh() {
        backgroundPullFromRemote();
    }
}
