package com.dicoding.mandoor.ui.account

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.mandoor.api.ApiConfig
import com.dicoding.mandoor.databinding.FragmentAccountBinding
import com.dicoding.mandoor.response.AccountDeleteResponse
import com.dicoding.mandoor.response.AccountGETResponse
import com.dicoding.mandoor.ui.Login.LoginActivity
import com.dicoding.mandoor.ui.account.email.EmailActivity
import com.dicoding.mandoor.ui.account.nama.NamaActivity
import com.dicoding.mandoor.ui.account.nohp.NomorHpActivity
import com.dicoding.mandoor.ui.account.username.UsernameActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        arguments?.getString("updatedFullName")?.let { newFullName ->
            binding.custname.text = newFullName
        }

        arguments?.getString("updatedUsername")?.let { newUsername ->
            binding.tvUsername.text = newUsername
        }

        arguments?.getString("updatedPhoneNumber")?.let { newPhoneNumber ->
            binding.nohp.text = newPhoneNumber
        }

        arguments?.getString("updatedEmail")?.let { newEmail ->
            binding.custemail.text = newEmail
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(context, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.linFullname.setOnClickListener {
            val intent = Intent(context, NamaActivity::class.java)
            startActivity(intent)
        }

        binding.linUsername.setOnClickListener {
            val intent = Intent(context, UsernameActivity::class.java)
            startActivity(intent)
        }

        binding.linEmail.setOnClickListener {
            val intent = Intent(context, EmailActivity::class.java)
            startActivity(intent)
        }

        binding.linNohp.setOnClickListener {
            val intent = Intent(context, NomorHpActivity::class.java)
            startActivity(intent)
        }

        binding.linHapusAkun.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Penghapusan Akun")
                .setMessage("Apakah Anda yakin ingin menghapus akun ini?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                    val token = sharedPreferences?.getString("user_token", null)

                    if (token.isNullOrEmpty()) {
                        Toast.makeText(context, "Token tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    deleteAccount(token)
                }
                .setNegativeButton("Tidak", null)
                .create()

            alertDialog.show()
        }

        getAccountData(token)

        binding.linLogout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("user_token")
            editor.apply()

            Toast.makeText(context, "Anda telah logout", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun getAccountData(token: String?) {
        ApiConfig.mainInstance.getAccount("Bearer $token").enqueue(object : Callback<AccountGETResponse> {
            override fun onResponse(
                call: Call<AccountGETResponse>,
                response: Response<AccountGETResponse>
            ) {
                if (response.isSuccessful) {
                    val accountData = response.body()
                    binding.tvUsername.text = accountData?.username ?: "Username not available"
                    binding.custemail.text = accountData?.email ?: "Email not available"
                    binding.custname.text = accountData?.fullName ?: "Full Name not available"
                    binding.nohp.text = accountData?.phoneNumber?.toString() ?: "Phone Number not available"
                } else {
                    Toast.makeText(context, "Gagal mengambil data akun: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountGETResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteAccount(token: String) {
        ApiConfig.mainInstance.deleteAccount("Bearer $token").enqueue(object : Callback<AccountGETResponse> {
            override fun onResponse(
                call: Call<AccountGETResponse>,
                response: Response<AccountGETResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show()

                    val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.remove("user_token")
                    editor?.apply()

                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Gagal menghapus akun: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountGETResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val updatedFullName = sharedPreferences.getString("user_full_name", "Default Name")
        val updatedUsername = sharedPreferences.getString("user_username", "Default Username")
        val updatedPhoneNumber = sharedPreferences.getString("user_phone_number", "Default Phone Number")
        val updatedEmail = sharedPreferences.getString("user_email", "Default Email")

        binding.custname.text = updatedFullName
        binding.tvUsername.text = updatedUsername
        binding.nohp.text = updatedPhoneNumber
        binding.custemail.text = updatedEmail
    }
}

