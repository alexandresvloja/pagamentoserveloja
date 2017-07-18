package br.com.servelojapagamento.utils;

/**
 * Created by alexandre on 04/07/2017.
 */

public class TransacaoEnum {

    public static class QntParcelas {
        public static final int A_VISTA = 1;
        public static final int DUAS_PARCELAS_SEM_JUROS = 2;
        public static final int TRES_PARCELAS_SEM_JUROS = 3;
        public static final int QUATRO_PARCELAS_SEM_JUROS = 4;
        public static final int CINCO_PARCELAS_SEM_JUROS = 5;
        public static final int SEIS_PARCELAS_SEM_JUROS = 6;
        public static final int SETE_PARCELAS_SEM_JUROS = 7;
        public static final int OITO_PARCELAS_SEM_JUROS = 8;
        public static final int NOVE_PARCELAS_SEM_JUROS = 9;
        public static final int DEZ_PARCELAS_SEM_JUROS = 10;
        public static final int ONZE_PARCELAS_SEM_JUROS = 11;
        public static final int DOZE_PARCELAS_SEM_JUROS = 12;
    }

    public static class TipoTransacao {
        public static final int DEBITO = 0;
        public static final int CREDITO = 1;
    }

    public static class StatusSeveloja {
        public static final int CARTAO_EXIGE_INFORMAR_CVV = 1;
        public static final int CARTAO_EXIGE_INFORMAR_SENHA = 2;
        public static final int TRANSAC_SERVELOJA_DEBITO_NAO_PERMITIDO = 3;
        public static final int ENVIANDO_TRANSACAO_SERVELOJA = 4;
        public static final int REGISTRO_TRANSACAO_SERVELOJA_FALHA = 5;
        public static final int REGISTRO_TRANSACAO_SERVELOJA_SUCESSO = 6;
        public static final int REGISTRO_TRANSACAO_SEGURA_SERVELOJA_FALHA = 7;
        public static final int REGISTRO_TRANSACAO_SEGURA_SERVELOJA_SUCESSO = 8;
        public static final int MENSAGEM_ERRO_OBSERVACAO = 9;
        public static final int PRECISA_CARREGAR_TABELAS = 10;
        public static final int OPERACAO_CANCELADA_PELO_USUARIO = 11;
        public static final int TRANSACAO_NAO_APROVADA = 12;
        public static final int SEM_CONEXAO_COM_INTERNET = 13;
        public static final int ERRO_GENÉRICO = 14;
        public static final int SEM_CONEXAO_COM_PINPAD = 15;
        public static final int VALOR_INVALIDO_PASSADO = 16;
        public static final int CARTAO_REMOVIDO_INDEVIDAMENTE = 17;
        public static final int ERRO_LEITURA_INFO_CARTAO = 18;
        public static final int DISPOSITIVO_NAO_COMPARTILHADO = 19;
        public static final int COMANDO_INESPERADO = 20;
        public static final int TIME_OUT = 21;
        public static final int RESPOSTA_PINPAD_NULL = 22;
        public static final int TRANSACAO_INVALIDA = 23;
        public static final int TRANSACAO_REJEITADA_PELO_AUTORIZADOR = 24;
        public static final int CARTAO_DE_CHIP_INSERIDO_PELA_TARJA = 25;
    }

}
