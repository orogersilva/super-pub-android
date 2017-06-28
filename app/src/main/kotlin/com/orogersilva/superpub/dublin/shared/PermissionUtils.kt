package com.orogersilva.superpub.dublin.shared

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

/**
 * Created by orogersilva on 6/27/2017.
 */
object PermissionUtils {

    // region PUBLIC METHODS

    fun requestPermissions(activity: AppCompatActivity, requestCode: Int, permissions: List<String>,
                           permissionRationaleDialogTitle: String, permissionRationaleDialogMessage: String,
                           permissionRationaleDialogPositiveButtonLabel: String,
                           permissionRationaleDialogNegativeButtonLabel: String,
                           finishActivity: Boolean) {

        if (shouldShowRequestPermissionRationale(activity, permissions)) {

            val rationaleDialog = PermissionUtils.RationaleDialog.newInstance(
                    permissionRationaleDialogTitle, permissionRationaleDialogMessage,
                    permissionRationaleDialogPositiveButtonLabel, permissionRationaleDialogNegativeButtonLabel,
                    permissions, requestCode, finishActivity)

            activity.supportFragmentManager
                    .beginTransaction()
                    .add(rationaleDialog, null)
                    .commitAllowingStateLoss()

        } else {

            ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
        }
    }

    fun requestPermissionsWithoutAskForRequestPermissionRationale(activity: AppCompatActivity, permissions: List<String>, requestCode: Int) {

        ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
    }

    fun isPermissionsGranted(grantResults: List<Int>): Boolean {

        grantResults.forEach {

            if (it != PackageManager.PERMISSION_GRANTED) return false
        }

        return true
    }

    fun shouldShowRequestPermissionRationale(activity: AppCompatActivity, permissions: List<String>): Boolean {

        permissions.forEach {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) return true
        }

        return false
    }

    // endregion

    // region CLASSES

    class RationaleDialog : DialogFragment() {

        // region PROPERTIES

        private lateinit var rationaleDialogListener: RationaleDialogListener

        private lateinit var title: String
        private lateinit var message: String
        private lateinit var positiveButtonLabel: String
        private lateinit var negativeButtonLabel: String
        private lateinit var permissions: List<String>

        private var finishActivity = false

        // endregion

        // region INTERFACES

        interface RationaleDialogListener {

            // region METHODS

            fun onRationaleDialogPositiveButtonClicked(requestCode: Int, permissions: List<String>)

            fun onRationaleDialogNegativeButtonClicked()

            // endregion
        }

        // endregion

        // region FACTORY METHODS

        companion object {

            fun newInstance(title: String, message: String, positiveButtonLabel: String,
                            negativeButtonLabel: String, permissions: List<String>, requestCode: Int,
                            finishActivity: Boolean): DialogFragment {

                val arguments = Bundle()

                arguments.putInt("request_code", requestCode)
                arguments.putBoolean("finish_activity", finishActivity)

                val dialog = RationaleDialog()

                dialog.title = title
                dialog.message = message
                dialog.positiveButtonLabel = positiveButtonLabel
                dialog.negativeButtonLabel = negativeButtonLabel

                dialog.permissions = permissions

                dialog.arguments = arguments

                return dialog
            }
        }

        // endregion

        // region OVERRIDED METHODS

        override fun onAttach(context: Context?) {

            super.onAttach(context)

            try {

                rationaleDialogListener = context as RationaleDialogListener

            } catch (e: ClassCastException) {

                throw ClassCastException(context.toString() + " must implement RationaleDialogListener.")
            }
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            val arguments = arguments

            val requestCode = arguments.getInt("request_code")
            finishActivity = arguments.getBoolean("finish_activity")

            val alertDialogBuilder = AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonLabel, object : DialogInterface.OnClickListener {

                        override fun onClick(dialogInterface: DialogInterface?, id: Int) {

                            rationaleDialogListener.onRationaleDialogPositiveButtonClicked(requestCode, permissions)
                        }
                    })
                    .setNegativeButton(negativeButtonLabel, object : DialogInterface.OnClickListener {

                        override fun onClick(dialogInterface: DialogInterface?, id: Int) {

                            rationaleDialogListener.onRationaleDialogNegativeButtonClicked()
                        }
                    })

            return alertDialogBuilder.create()
        }

        override fun dismiss() {

            super.dismiss()

            if (finishActivity) activity.finish()
        }

        // endregion
    }

    class PermissionDeniedDialog private constructor() : DialogFragment() {

        // region PROPERTIES

        private lateinit var permissionDeniedDialogListener: PermissionDeniedDialogListener

        private lateinit var title: String
        private lateinit var message: String
        private lateinit var enableButtonLabel: String

        private var finishActivity = false

        // endregion

        // region INTERFACES

        interface PermissionDeniedDialogListener {

            // region METHODS

            fun onPermissionDeniedDialogEnablingButtonClicked()

            // endregion
        }

        // endregion

        // region FACTORY METHODS

        companion object {

            // region METHODS

            fun newInstance(title: String, message: String, enableButtonLabel: String,
                            finishActivity: Boolean): DialogFragment {

                val arguments = Bundle()

                arguments.putBoolean("finish_activity", finishActivity)

                val dialog = PermissionDeniedDialog()

                dialog.title = title
                dialog.message = message
                dialog.enableButtonLabel = enableButtonLabel

                dialog.arguments = arguments

                return dialog
            }

            // endregion
        }

        // endregion

        // region OVERRIDED METHODS

        override fun onAttach(context: Context?) {

            super.onAttach(context)

            try {

                permissionDeniedDialogListener = context as PermissionDeniedDialogListener

            } catch (e: ClassCastException) {

                throw ClassCastException(context.toString() + " must implement PermissionDeniedDialogListener.")
            }
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            finishActivity = arguments.getBoolean("finish_activity")

            val alertDialogBuilder = AlertDialog.Builder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(enableButtonLabel, object : DialogInterface.OnClickListener {

                        override fun onClick(dialogInterface: DialogInterface?, id: Int) {

                            permissionDeniedDialogListener.onPermissionDeniedDialogEnablingButtonClicked()
                        }
                    })

            return alertDialogBuilder.create()
        }

        override fun onDismiss(dialog: DialogInterface?) {

            super.onDismiss(dialog)

            if (finishActivity) activity.finish()
        }

        // endregion
    }

    // endregion
}