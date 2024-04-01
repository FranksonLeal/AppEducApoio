package com.example.educapoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.educapoio.databinding.ActivityCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class cadastro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.btnCriarConta.setOnClickListener(v -> validaDados());

        Button btnVoltarLogin = findViewById(R.id.btnVoltarLogin);
        btnVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ao clicar no botão, iniciar a atividade de login
                startActivity(new Intent(cadastro.this, login.class));
                // Finalizar a atividade atual para não retornar a ela ao pressionar o botão "voltar"
                finish();
            }
        });
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty() && !senha.isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            criarContaFirebase(email, senha);
        } else {
            Toast.makeText(this, "Informe seu email e senha", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarContaFirebase(String email, String senha){
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        // Cadastro bem-sucedido, redirecione o usuário para a tela de mensagem
                        startActivity(new Intent(cadastro.this, mensagemCadastro.class));
                        finish(); // Finaliza a atividade de cadastro para evitar que o usuário retorne a ela pressionando o botão "voltar"
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Ocorreu algum erro durante o cadastro!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

