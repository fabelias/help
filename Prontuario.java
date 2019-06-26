import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

public class Prontuario {

	private String nomePaciente;
	private Internacao internacao;
	private Set<Procedimento> procedimentos = new HashSet<Procedimento>();

	public Prontuario(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public void setInternacao(Internacao internacao) {
		this.internacao = internacao;
	}

	public void addProcedimento(Procedimento procedimento) {
		this.procedimentos.add(procedimento);
	}

	public String imprimaConta() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		String conta = "----------------------------------------------------------------------------------------------";

		float valorDiarias = 0.0f;

		// Contabilizar as diárias
		if (internacao != null) {
			switch (internacao.getTipoLeito()) {
				case ENFERMARIA:
					if (internacao.getQtdeDias() <= 3) {
						valorDiarias += 40.00 * internacao.getQtdeDias(); // Internação Básica
					} else if (internacao.getQtdeDias() <= 8) {
						valorDiarias += 35.00 * internacao.getQtdeDias(); // Internação Média
					} else {
						valorDiarias += 30.00 * internacao.getQtdeDias(); // Internação Grave
					}
					break;
				case APARTAMENTO:
					if (internacao.getQtdeDias() <= 3) {
						valorDiarias += 100.00 * internacao.getQtdeDias(); // Internação Básica
					} else if (internacao.getQtdeDias() <= 8) {
						valorDiarias += 90.00 * internacao.getQtdeDias();  // Internação Média
					} else {
						valorDiarias += 80.00 * internacao.getQtdeDias();  // Internação Grave
					}
					break;
			}
		}

		float valorTotalProcedimentos = 0.00f;
		int qtdeProcedimentosBasicos = 0;
		int qtdeProcedimentosComuns = 0;
		int qtdeProcedimentosAvancados = 0;

		//Contabiliza os procedimentos
		for (Procedimento procedimento : procedimentos) {
			switch (procedimento.getTipoProcedimento()) {
				case BASICO:
					qtdeProcedimentosBasicos++;
					valorTotalProcedimentos += 50.00;
					break;

				case COMUM:
					qtdeProcedimentosComuns++;
					valorTotalProcedimentos += 150.00;
					break;

				case AVANCADO:
					qtdeProcedimentosAvancados++;
					valorTotalProcedimentos += 500.00;
					break;
			}
		}

		conta += "\nA conta do(a) paciente $nomePaciente tem valor total de __ " + formatter.format(valorDiarias + valorTotalProcedimentos) + " __";
		conta += "\n\nConforme os detalhes abaixo:";

		if (internacao != null) {
			conta += "\n\nValor Total Diárias:\t\t\t" + formatter.format(valorDiarias);
			conta += "\n\t\t\t\t\t" + internacao.getQtdeDias() + " diária" + (internacao.getQtdeDias() > 1 ? "s" : "")
					+ " em " + (internacao.getTipoLeito() == TipoLeito.APARTAMENTO ? "apartamento" : "enfermaria");
		}

		if (procedimentos.size() > 0) {
			conta += "\n\nValor Total Procedimentos:\t\t" + formatter.format(valorTotalProcedimentos);

			if (qtdeProcedimentosBasicos > 0) {
				conta += "\n\t\t\t\t\t" + qtdeProcedimentosBasicos + " procedimento" + (qtdeProcedimentosBasicos > 1 ? "s" : "")
						+ " básico" + (qtdeProcedimentosBasicos > 1 ? "s" : "");
			}

			if (qtdeProcedimentosComuns > 0) {
				conta += "\n\t\t\t\t\t" + qtdeProcedimentosComuns + " procedimento" + (qtdeProcedimentosComuns > 1 ? "s" : "")
						+ " comu" + (qtdeProcedimentosComuns > 1 ? "ns" : "m");
			}

			if (qtdeProcedimentosAvancados > 0) {
				conta += "\n\t\t\t\t\t" + qtdeProcedimentosAvancados + " procedimento" + (qtdeProcedimentosBasicos > 1 ? "s" : "")
						+ " avançado" + (qtdeProcedimentosAvancados > 1 ? "s" : "");
			}
		}

		conta += "\n\nVolte sempre, a casa é sua!";
		conta += "\n----------------------------------------------------------------------------------------------";

		return conta;
	}

}
