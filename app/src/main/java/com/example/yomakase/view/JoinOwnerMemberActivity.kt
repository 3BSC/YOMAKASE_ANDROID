package com.example.yomakase.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yomakase.databinding.ActivityJoinOwnerMemberBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.Manifest
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.yomakase.*
import com.example.yomakase.adapter.ClosedDayAdapter
import com.example.yomakase.adapter.FacilityAdapter
import com.example.yomakase.adapter.PriceAdapter
import com.example.yomakase.model.retrofit.join_owner_member.JoinOwnerMemberReq
import com.example.yomakase.model.rv_item.ClosedDay
import com.example.yomakase.model.rv_item.DialogFacility
import com.example.yomakase.model.rv_item.Price
import com.example.yomakase.viewmodel.JoinOwnerMemberViewModel

class JoinOwnerMemberActivity : AppCompatActivity(){

    private lateinit var binding: ActivityJoinOwnerMemberBinding
    private lateinit var viewModel: JoinOwnerMemberViewModel
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var pwVisible = false

    private val emailRegex = Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private val passwordRegex = Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$")
    private val phoneRegex = Regex("^[0-9]+$")

    private val cal: Calendar = Calendar.getInstance()
    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        birth = dateFormat.format(Date(year- 1900, month, dayOfMonth))
        binding.tvBirth.text = birth
    }

    private var imageFile: File? = null
    private var email : String? = null
    private var nickname: String? = null
    private var birth: String? = null

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermitted ->
            if (isPermitted){
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                getBusinessRegistrationLauncher.launch(intent)
            }else{
                Toast.makeText(this, "갤러리 권한이 거부 되었습니다.", Toast.LENGTH_LONG).show()
            }
        }

    private val getBusinessRegistrationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    imageFile = File(getRealPathFromUri(it))
                }
                if (imageFile != null)
                    binding.tvBusinessRegistration.text = "첨부됨"
            }
        }

    private val getAddressLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                binding.tvMainAddress.text = result.data!!.getStringExtra("data")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_owner_member)
        viewModel = ViewModelProvider(this)[JoinOwnerMemberViewModel::class.java]
        setUp()

        viewModel.emailResult.observe(this, androidx.lifecycle.Observer {
            //email = it.email
        })

        viewModel.nicknameResult.observe(this, androidx.lifecycle.Observer {
            //nickname = it.nickname
        })

        viewModel.joinGeneralMemberResult.observe(this, androidx.lifecycle.Observer {
            //
        })
    }

    private fun setUp(){
        setupSpinnerCategory()
        setupSpinnerEmailDomain()
        setupSpinnerHandler()
    }

    fun reqJoinOwnerMember(view: View){
        if(isEmptyInput())
            Toast.makeText(this, "필수 입력란은 모두 입력되어야합니다.", Toast.LENGTH_SHORT).show()

        else if (email == null)
            Toast.makeText(this, "이메일 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
        else if(nickname == null)
            Toast.makeText(this, "닉네임 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
        else if(!passwordValidator())
        else if(!phoneValidator() || !storeTelValidator())
            Toast.makeText(this, "번호는 -를 빼고 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
        else {
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            /*
            viewModel.reqJoinGeneralMember(JoinOwnerMemberReq(
                values
            ))
             */
        }
    }

    private fun isEmptyInput(): Boolean{
        if(binding.etPassword.text.isEmpty() ||
                    binding.etPasswordCheck.text.toString().isEmpty()||
                    binding.tvBirth.text.isEmpty()||
                    binding.etPhoneNumber.text.isEmpty()||
                    binding.etStoreName.text.isEmpty()||
                    binding.etStoreTel.text.isEmpty()||
                    binding.tvMainAddress.text.isEmpty()||
                    binding.etSubAddress.text.isEmpty()||
                    imageFile == null)
            return true
        return false
    }

    fun addressFind(view: View){
        getAddressLauncher.launch(Intent(this, KakaoAddressFindActivity::class.java))
    }

    fun emailValidator(view: View){
        if (binding.etEmail.text.isEmpty())
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        else {
            var id = binding.etEmail.text.toString()
            val domain = binding.spEmailDomain.selectedItem.toString()
            if (domain != "직접입력")
                id += domain

            if (!id.matches(emailRegex))
                Toast.makeText(this, "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            //else
            //viewModel.emailDuplicatedCheck(id)
        }
    }

    fun nicknameValidator(view: View){
        val nickname = binding.etNickname.text.toString()
        if (nickname.isEmpty())
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
        else{
            //viewModel.nicknameDuplicatedCheck(nickname)
        }
    }

    private fun phoneValidator(): Boolean{
        val phone = binding.etPhoneNumber.text
        if (!phone.matches(phoneRegex))
            return false
        return true
    }

    private fun storeTelValidator(): Boolean{
        val phone = binding.etStoreTel.text
        if (!phone.matches(phoneRegex))
            return false
        return true
    }

    private fun passwordValidator(): Boolean{
        val password = binding.etPassword.text
        val passwordCheck = binding.etPasswordCheck.text.toString()

        //정규표현식 확인
        if (!password.toString().matches(passwordRegex)) {
            Toast.makeText(
                this,
                "영어, 숫자, 특수기호를 포함한 8자리 이상의 비밀번호를 설정해주세요.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            if (password.toString() != passwordCheck) {
                Toast.makeText(this, "비밀번호와 비밀번화 확인란이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }


    private fun setupSpinnerCategory() {
        val categories = resources.getStringArray(R.array.sp_category)
        val adapter = ArrayAdapter(this, R.layout.spinner_text, categories)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spCategory.adapter = adapter
    }

    private fun setupSpinnerEmailDomain() {
        val domains = resources.getStringArray(R.array.sp_email_domain)
        val adapter = ArrayAdapter(this, R.layout.spinner_text, domains)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding.spEmailDomain.adapter = adapter
    }

    private fun setupSpinnerHandler() {
        binding.spEmailDomain.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("email", "onItemSelected: ${binding.spEmailDomain.getItemAtPosition(position)}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("category", "onItemSelected: ${binding.spCategory.getItemAtPosition(p2)} ")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun datePicker(view: View){
        DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar
            ,dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun visiblePassword(view: View){
        when(pwVisible){
            false -> {
                pwVisible = true
                binding.etPasswordCheck.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.btnPasswordHide.setImageResource(R.drawable.ic_hide)
            }
            true -> {
                pwVisible = false
                binding.etPasswordCheck.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnPasswordHide.setImageResource(R.drawable.ic_show)
            }
        }
        binding.etPasswordCheck.setTextAppearance(R.style.join_text)
        binding.etPasswordCheck.setSelection(binding.etPasswordCheck.text!!.length)
    }


    private fun getRealPathFromUri(uri: Uri): String{
        val buildName = Build.MANUFACTURER
        if (buildName.equals("Xiaomi")) return uri.path!!

        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)

        if (cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    fun openGallery(view: View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        else
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}