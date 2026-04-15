package com.thiagocarneiro.estudo.estudospring.enums;

public enum UserRole {

    ROOT,           // Super administrador do sistema (Você/Desenvolvedor)
    ADMIN_EMPRESA,  // Dono/Gestor da empresa cliente (Acesso Desktop total da org)
    USER_EMPRESA,   // Funcionário da empresa (Acesso Desktop operacional)
    USER_PESSOAL  // Usuário final pessoa física (Acesso focado no Mobile)

}
