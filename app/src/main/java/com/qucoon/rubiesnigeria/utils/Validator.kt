package com.qucoon.rubiesnigeria.utils


import android.util.Patterns
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

/**
 *
 *
 * This class is used to validate data like email, phone, password policy, etc.
 * It also sets error to the EditText or TextInputLayout of the EditText if needed.
 */
class Validator {

    companion object {

        private val PASSWORD_POLICY4 = "Password Does Not Match"

        // Default validation messages
        private val PASSWORD_POLICY2 = """Password should be minimum 4 characters long,
            |should contain letter,
            |at least one number 
//            |at least one special character among ~!@#$%^&*()-_=+|[]{};:'\",<.>/?""".trimMargin()
        private val PASSWORD_POLICY = """Password should be minimum 3 characters long,
            |should contain letter,
            |at least one number 
//            |at least one special character among ~!@#$%^&*()-_=+|[]{};:'\",<.>/?""".trimMargin()

        //        private val RESET_PASSWORD_ERROR = "Password should be minimum 4 characters long and maximum 20 characters long,\n" +
//                "should contain at least one capital letter,\n" +
//                "at least one small letter,\n" +
//                "at least one number and\n" +
//                "at least one special character among ~!@#\$%^&*()-_=+|[]{};:'\\\",<.>/?"
        private val RESET_PASSWORD_ERROR =
            "Password should be a minimum of four characters and must contain uppercase,/n lowercase and special characters."
        private val OTHER_NAME_VALIDATION_MSG = "Enter a valid Other Name"
        private val SURNAME_NAME_VALIDATION_MSG = "Enter a valid Surname Name"
        private val COUMTRY_VALIDATION_MSG = "Select a Country"
        private val GENDER_VALIDATION_MSG = "Enter your gender"
        private val ADDRESS_VALIDATION_MSG = "Enter a valid Address"
        private val LOCALGOVT_VALIDATION_MSG = "Enter a valid Local Government"
        private val NAME_VALIDATION_MSG = "Enter a valid name"
        private val FIRST_NAME_VALIDATION_MSG = "Enter your first name"
        private val MIDDLE_NAME_VALIDATION_MSG = "Enter a valid Middle Name"
        private val LAST_NAME_VALIDATION_MSG = "Enter your last name"
        private val BVN_NAME_VALIDATION_MSG = "Enter a valid BVN"
        private val EMAIL_VALIDATION_MSG = "Enter a valid Email Address"
        private val PHONE_VALIDATION_MSG = "Enter a valid Phone Number"
        private val PHONE_CODE_VALIDATION_MSG = "Enter a valid Phone Code"
        private val PHONE_NUMBER_VALIDATION_MSG = "Enter a valid Phone Number"
        private val USERNAME_VALIDATION_MSG = "Enter a valid username"
        private val SORT_CODE_VALIDATION_MESSAGE = "Enter a valid sortcode"
        private val ALIAS_VALIDATION_MSG = "Enter a valid alias"
        private val DOB_VALIDATION_MSG = "Select a Date of Birth"
        private val PASSWORD_POLICY3 = "Enter a valid password"
        private val PASSWORD_POLICY8 = "Enter a valid Password"
        private val ACCOUNT_NUMBER_VALIDATION_MESSAGE = "Enter 8 digit account number"
        private val NIN_VALIDATION_MESSAGE = "Enter 11 digit NIN"
        private val LICENSE_VALIDATION_MESSAGE = "Enter valid Drivers License"
        private val VC_VALIDATION_MESSAGE = "Enter valid Voters Card"
        private val PASSPORT_VALIDATION_MESSAGE = "Enter valid Passport Number"
        private val PASSWORD_POLICY_NEW = """Password should be minimum 3 characters long,
            |at least one special character among ~!@#$%^&*()-_=+|[]{};:'\",<.>/?""".trimMargin()

        private val OCCUPATION = "Enter a valid occupation"
        private val CITY = "Enter a valid city"
        private val POSTLACODE = "Enter a valid postal code"
        private val SOURCEOFFUND = "Enter a valid source of fund"

        /**
         * Retrieve string data from the parameter.
         * @param data - can be EditText or String
         * @return - String extracted from EditText or data if its data type is Strin.
         */
        private fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.trim().toString()
            } else if (data is String) {
                str = data.trim()
            }
            return str
        }

        /**
         * Checks if the name is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the name is valid.
         */
        fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidFirstName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else FIRST_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isValidMiddleName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else MIDDLE_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidDOB(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else DOB_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isValidOccupation(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else OCCUPATION
                setError(data, error)
            }

            return valid
        }

        fun isValidCity(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else CITY
                setError(data, error)
            }

            return valid
        }

        fun isValidPostalCode(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else POSTLACODE
                setError(data, error)
            }
            return valid
        }


        fun isValidSourceOfFund(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else SOURCEOFFUND
                setError(data, error)
            }
            return valid
        }


        fun isValidBVN(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 11

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else BVN_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isValidLastName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else LAST_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidUserName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else USERNAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidAlias(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 3

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else USERNAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidSortCode(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 8

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else SORT_CODE_VALIDATION_MESSAGE
                setError(data, error)
            }

            return valid
        }

        fun isValidAccountNumber(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 8

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else ACCOUNT_NUMBER_VALIDATION_MESSAGE
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the email is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the email is valid.
         */
        fun isValidEmail(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = Patterns.EMAIL_ADDRESS.matcher(str).matches()


            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else EMAIL_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the phone is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the phone is valid.
         */
        fun isValidPhone(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 11

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PHONE_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidPhoneCode(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 11

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PHONE_CODE_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidPhoneNumber(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 10

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PHONE_NUMBER_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        /**
         * Checks if the password is valid as per the following password policy.
         * Password should be minimum minimum 8 characters long.
         * Password should contain at least one number.
         * Password should contain at least one capital letter.
         * Password should contain at least one small letter.
         * Password should contain at least one special character.
         * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
         *
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the password is valid as per the password policy.
         */
        fun isValidPassword(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password policy check
            // Password should be minimum minimum 3 characters long
            if (str.length < 3) {
                valid = false
            }
            // Password should contain at least one number
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }


            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY
                setError(data, error)
            }

            return valid
        }

        fun isValidNIN(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 11

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else NIN_VALIDATION_MESSAGE
                setError(data, error)
            }

            return valid
        }

        fun isValidOtherName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else OTHER_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidSurname(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else SURNAME_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isValidLicense(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 12

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else LICENSE_VALIDATION_MESSAGE
                setError(data, error)
            }

            return valid
        }

        fun isValidCountry(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else COUMTRY_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidAddress(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else ADDRESS_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidGender(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            if (updateUI) {
                val error: String? = if (valid) null else GENDER_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidLocalGovt(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else LOCALGOVT_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isValidVotersCardNumber(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 15

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else VC_VALIDATION_MESSAGE
                setError(data, error)
            }

            return valid
        }

        fun isValidPassport(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 9

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSPORT_VALIDATION_MESSAGE
                setError(data, error)
            }

            return valid
        }

        fun isValidNullPassword(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            var exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            var pattern = Pattern.compile(exp)
            var matcher = pattern.matcher(str)
            if (matcher.matches()) {
                valid = false
            }

            if (str.trim().length < 3) {
                valid = false
            }

            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY_NEW
                setError(data, error)
            }

            return valid
        }

        fun isValidPassword3(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 8) {
                valid = false
            }
            // Password should contain at least one number
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one capital letter
            exp = ".*[A-Z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one small letter
            exp = ".*[a-z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY
                setError(data, error)
            }

            return valid
        }

        fun isValidPassword4(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 8) {
                valid = false
            }
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY3
                setError(data, error)
            }

            return valid
        }

        fun isValidPassword8(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 4) {
                valid = false
            }
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY8
                setError(data, error)
            }

            return valid
        }

        fun isValidPassword2(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password should contain at least one number
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(str)

            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 4) {
                valid = false
            }

            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                // Password policy check
                // Password should be minimum minimum 8 characters long
                if (str.length != 20) {
                    valid = false
                }


                // Set error if required
                if (updateUI) {
                    val error: String? = if (valid) null else PASSWORD_POLICY2
                    setError(data, error)
                }

            }
            return valid

        }

        fun isValidPassword5(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 4 || str.length > 20) {
                valid = false
            }
            // Password should contain at least one number
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one capital letter
            exp = ".*[A-Z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one small letter
            exp = ".*[a-z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }

            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else RESET_PASSWORD_ERROR
                setError(data, error)
            }

            return valid
        }


        fun isValidPasswordMatch(data: Any, data2: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val str2 = getText(data2)

            var valid = true

            // Password policy check
            // Password must tally
            if (!str.contentEquals(str2)) {
                valid = false
            }


            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY4
                setError(data2, error)
            }

            return valid
        }


        /**
         * Sets error on EditText or TextInputLayout of the EditText.
         * @param data - Should be EditText
         * @param error - Message to be shown as error, can be null if no error is to be set
         */

        private fun setError(data: Any, error: String?) {
            if (data is EditText) {
                if (data.parent.parent is TextInputLayout) {
                    (data.parent.parent as TextInputLayout).error = error
                } else {
                    data.error = error
                }
            }
        }


    }
}

