abstract class Conta {
    protected String idConta;
    protected String nomeTitular;
    protected double valorAtual;

    public Conta(String idConta, String nomeTitular, double valorAtual) {
        this.idConta = idConta;
        this.nomeTitular = nomeTitular;
        this.valorAtual = valorAtual;
    }

    public void adicionarSaldo(double valor) {
        valorAtual += valor;
        System.out.println("Depósito realizado. Novo saldo: " + valorAtual);
    }

    public abstract boolean retirarSaldo(double valor);

    public void mostrarDados() {
        System.out.println("Conta: " + idConta + " | Titular: " + nomeTitular + " | Saldo: " + valorAtual);
    }
}

class Corrente extends Conta {
    private double limiteCredito;

    public Corrente(String idConta, String nomeTitular, double valorAtual, double limiteCredito) {
        super(idConta, nomeTitular, valorAtual);
        this.limiteCredito = limiteCredito;
    }

    @Override
    public boolean retirarSaldo(double valor) {
        if (valorAtual + limiteCredito >= valor) {
            valorAtual -= valor;
            System.out.println("Saque realizado. Novo saldo: " + valorAtual);
            return true;
        }
        System.out.println("Saldo insuficiente, mesmo com limite.");
        return false;
    }
}

class Poupanca extends Conta {
    public Poupanca(String idConta, String nomeTitular, double valorAtual) {
        super(idConta, nomeTitular, valorAtual);
    }

    @Override
    public boolean retirarSaldo(double valor) {
        if (valorAtual >= valor) {
            valorAtual -= valor;
            System.out.println("Saque realizado. Novo saldo: " + valorAtual);
            return true;
        }
        System.out.println("Saldo insuficiente para saque.");
        return false;
    }
}

class Investimento extends Conta {
    public Investimento(String idConta, String nomeTitular, double valorAtual) {
        super(idConta, nomeTitular, valorAtual);
    }

    @Override
    public boolean retirarSaldo(double valor) {
        double taxa = valor * 0.02;
        double total = valor + taxa;
        if (valorAtual >= total) {
            valorAtual -= total;
            System.out.println("Saque realizado com taxa de 2%. Novo saldo: " + valorAtual);
            return true;
        }
        System.out.println("Saldo insuficiente para saque com taxa.");
        return false;
    }
}

class Salario extends Corrente {
    private int saquesPermitidos = 1;

    public Salario(String idConta, String nomeTitular, double valorAtual, double limiteCredito) {
        super(idConta, nomeTitular, valorAtual, limiteCredito);
    }

    @Override
    public boolean retirarSaldo(double valor) {
        if (saquesPermitidos > 0) {
            saquesPermitidos--;
            return super.retirarSaldo(valor);
        } else {
            return super.retirarSaldo(valor + 5);
        }
    }
}

class InvestimentoAltoRisco extends Investimento {
    public InvestimentoAltoRisco(String idConta, String nomeTitular, double valorAtual) {
        super(idConta, nomeTitular, valorAtual);
    }

    @Override
    public boolean retirarSaldo(double valor) {
        double taxa = valor * 0.05;
        double total = valor + taxa;
        if (valorAtual >= total && valorAtual >= 10000) {
            valorAtual -= total;
            System.out.println("Saque realizado com taxa de 5%. Novo saldo: " + valorAtual);
            return true;
        }
        System.out.println("Saldo insuficiente ou mínimo de R$ 10.000,00 necessário.");
        return false;
    }
}

public class Principal {
    public static void main(String[] args) {
        Conta cc = new Corrente("12345", "Lucas", 1000, 500);
        cc.mostrarDados();
        cc.retirarSaldo(1200);
        cc.adicionarSaldo(500);
        
        Conta cp = new Poupanca("67890", "Juliana", 2000);
        cp.mostrarDados();
        cp.retirarSaldo(2500);
        
        Conta ci = new Investimento("54321", "Rafael", 5000);
        ci.mostrarDados();
        ci.retirarSaldo(1000);
        
        Conta csa = new Salario("11223", "Fernanda", 3000, 0);
        csa.mostrarDados();
        csa.retirarSaldo(500);
        csa.retirarSaldo(500);
        
        Conta cia = new InvestimentoAltoRisco("99887", "Ricardo", 15000);
        cia.mostrarDados();
        cia.retirarSaldo(2000);
    }
}
