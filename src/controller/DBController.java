package controller;
import model.*;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.logging.Logger;

import utils.LoggerLoader;

/**
 * Classe responsável por controlar as operações de acesso à base de dados.
 * Contém métodos para inserir, atualizar e consultar dados relacionados a logs, utilizadores, equipamentos, certificações e notificações.
 */
public class DBController {
    private Connection conexao;
    Scanner scanner = new Scanner(System.in);
    public DBController(Connection conexao) {
        this.conexao = conexao;
    }
    Logger logger = LoggerLoader.getLogger();

    public List<Log> getLogs(String username) {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE user_username = ? ORDER BY data DESC";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp dataHora = rs.getTimestamp("data_hora");
                String descricao = rs.getString("acao");
                Log log = new Log(username, descricao);
                logs.add(log);
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao obter logs: \033[0m" + e.getMessage());
        }
        return logs;
    }

    /**
     * Função para enviar um log para a base de dados. Recebe um objeto do tipo Log e insere na tabela logs.
     * @param log
     * @return true or false
     */
    public boolean enviarLog(Log log){
        String sql = "INSERT INTO logs (user_username, acao) VALUES (?, ?)";
        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, log.getUsername());
            stmt.setString(2, log.getAcao());

            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            System.err.println("\033[31mErro ao enviar log: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para inserir uma observação na base de dados. Recebe um objeto do tipo Observacao e insere na tabela observacoes.
     * @param observacao
     * @return true or false
     */
    public boolean inserirObservacao(Observacao observacao) {
        String sql = "INSERT INTO observacoes (observacao, id_utilizador, assunto, informacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, observacao.getObservacao());
            stmt.setInt(2, observacao.getId_utilizador());
            stmt.setString(3, observacao.getAssunto());
            stmt.setString(4, observacao.getInformacao());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir observação: \033[0m" + e.getMessage());
            return false;
        }
    }
    /**
     * Função para obter a imagem do utilizador. Recebe o username e devolve o caminho da imagem do utilizador.
     * Se não existir imagem, devolve uma imagem padrão.
     * @param username
     * @return caminho da imagem do utilizador
     */
    public String getUserImage(String username) {
        String sql = "SELECT img_path FROM utilizadores WHERE username = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String imagem = rs.getString("img_path");
                if (imagem == null || imagem.isEmpty()) {
                    return "public/imgs/user/profile/default_profile_img.png"; // Devolve default se não existir imagem
                }
                return imagem;
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao obter imagem do utilizador: \033[0m" + e.getMessage());
        }
        return "public/imgs/user/profile/default_profile_img.png"; // Devolve default em caso de erro ou se não existir o utilizador
    }

    /**
     * Função para inserir a imagem do utilizador na base de dados. Recebe o username e o caminho da imagem e atualiza a tabela utilizadores.
     * @param username
     * @param imagePath
     */
    public void insertUserImage(String username, String imagePath) {
        String sql = "UPDATE utilizadores SET img_path = ? WHERE username = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, imagePath);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir imagem do utilizador: \033[0m" + e.getMessage());
        }
    }

    /**
     * Função para adicionar um teste á base de dados. Este teste é executado por um técnino na hora de validar uma certificação.
     * @param teste
     * @return true or false
     */
    public boolean adicionarTeste(Teste teste){
        String sql = "INSERT INTO testes (id_equipamento, designacao, descricao, valor_medido) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, teste.getId_equipamento());
            stmt.setString(2, teste.getDesignacao());
            stmt.setString(3, teste.getDescricao());
            stmt.setInt(4, teste.getValor_medido());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir teste: \033[0m" + e.getMessage());
            return false;
        }
    }


    /**
     * Função para adicionar um equipameto á base de dados. Recebe um objeto do tipo Equipamento e insere na tabela equipamentos.
     * @param equipamento
     * @return true or false
     */
    public boolean adicionarEquipamentos(Equipamento equipamento) {
        String sql = "INSERT INTO equipamentos (id_fabricante, marca, modelo, setor_comercial, potencia, amperagem, codigo_sku, numero_modelo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sql_sku = "SELECT codigo_sku FROM equipamentos";

        Random random = new Random();
        long codigo_sku_random = random.nextInt(1000000);
        boolean skuExiste;

        try (PreparedStatement stmt = conexao.prepareStatement(sql_sku);
             ResultSet rs = stmt.executeQuery()) {

            do {
                skuExiste = false;
                while (rs.next()) {
                    if (rs.getInt("codigo_sku") == codigo_sku_random) {
                        codigo_sku_random = random.nextInt(1000000);
                        skuExiste = true;
                    }
                }
            } while (skuExiste);
            Log log = new Log(Integer.toString(equipamento.getId_user()), "Equipamento adicionado com sucesso. SKU: " + codigo_sku_random);
            enviarLog(log);
            logger.info("Equipamento adicionado com sucesso. SKU: " + codigo_sku_random);

        } catch (SQLException e) {
            System.err.println("\033[31mErro ao verificar código SKU: \033[0m" + e.getMessage());
            return false;
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, equipamento.getId_user());
            stmt.setString(2, equipamento.getMarca());
            stmt.setString(3, equipamento.getModelo());
            stmt.setString(4, equipamento.getSetor_comercial());
            stmt.setInt(5, equipamento.getPotencia());
            stmt.setInt(6, equipamento.getAmperagem());
            stmt.setLong(7, codigo_sku_random);
            stmt.setInt(8, equipamento.getNumero_modelo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir equipamento: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para encriptar a password (Message Digest) do utilizador.
     * @param password
     * @return
     * @throws Exception
     */
    public String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        }catch (Exception e){
            System.err.println("\033[31mErro ao encriptar a password: \033[0m" + e.getMessage());
        }
        return null;
    }
    /**
     * Função para enviar uma notificação para a bd. Recebe o id do utilizador, a descrição, o tipo e o encarregado e insere na tabela notificações.
     * @param idUtilizador
     * @param descricao
     * @param tipo
     * @param encarregado
     * @return true or false
     */
    public boolean enviarNotificacao(int idUtilizador, String descricao, String tipo, String encarregado){
        String sql = "INSERT INTO notificacoes (id_utilizador, descricao, tipo, encarregado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUtilizador);
            stmt.setString(2, descricao);
            stmt.setString(3, tipo);
            stmt.setString(4, encarregado);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        }catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir utilizador: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para listar todas as notificações por determiados encarregados
     * @param encarregado
     */
    public Notificacao[] listarNotificacoes(String encarregado) {
        List<Notificacao> notificacoes = new ArrayList<>();
        String sql = "SELECT * FROM notificacoes WHERE encarregado = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, encarregado);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notificacao notificacao = new Notificacao(
                            rs.getTimestamp("data_hora"),
                            rs.getInt("id_utilizador"),
                            rs.getString("descricao"),
                            rs.getString("tipo"),
                            rs.getString("encarregado"),
                            rs.getBoolean("lida")
                    );
                    notificacoes.add(notificacao);
                }
            }
        } catch (Exception e) {
            System.out.println("\033[31mErro ao listar notificações: \033[0m" + e.getMessage());
        }

        return notificacoes.toArray(new Notificacao[0]);
    }

    /**
     * Função para listar todos os utilizadores da base de dados. Devolve um array de utilizadores.
     * @return array de utilizadores
     */
    public Utilizador[] listarUtilizadoresInterface() {
        List<Utilizador> utilizadores = new ArrayList<>();
        String sql = "SELECT * FROM utilizadores";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Utilizador utilizador = new Utilizador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        rs.getString("nif"),
                        rs.getString("telefone"),
                        rs.getString("morada"),
                        rs.getString("sector_comercial"),
                        rs.getString("area_especializacao"),
                        rs.getInt("nivel_certificacao")
                );
                utilizadores.add(utilizador);
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao listar utilizadores: \033[0m" + e.getMessage());
        }
        return utilizadores.toArray(new Utilizador[0]);
    }

    /**
     * Função para listar os equipamentos de um fabricante. Recebe o id do fabricante e devolve um array de equipamentos.
     * @param id_fabricante
     * @return
     */
    public Equipamento[] listarEquipamentosInterface(int id_fabricante) {
        List<Equipamento> equipamentos = new ArrayList<>();
        String sql = "SELECT * FROM equipamentos where id_fabricante = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
             stmt.setInt(1, id_fabricante);
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Equipamento equipamento = new Equipamento(
                        rs.getInt("id_equipamento"),
                        rs.getInt("id_fabricante"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("setor_comercial"),
                        rs.getInt("potencia"),
                        rs.getInt("amperagem"),
                        rs.getInt("codigo_sku"),
                        rs.getInt("numero_modelo"),
                        rs.getString("data_submissao"),
                        rs.getString("data_certeficacao")

                );
                equipamentos.add(equipamento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao Equipamentos: " + e.getMessage());
        }
        return equipamentos.toArray(new Equipamento[0]);
    }

    /**
     * Função para listar os equipamentos de um fabricante onde um técnico esteja associado. Recebe o id do técnico e devolve um array de equipamentos.
     * @param id_tech
     * @return array de equipamentos
     */
    public Equipamento[] listarEquipamentosInterfaceTech(int id_tech){
        List<Equipamento> equipamentos = new ArrayList<>();
        String sql = "SELECT equipamentos.* from equipamentos JOIN certificacoes ON  certificacoes.id_equipamento = equipamentos.id_equipamento\n" +
                "WHERE certificacoes.id_tecnico = ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, id_tech);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Equipamento equipamento = new Equipamento(
                        rs.getInt("id_equipamento"),
                        rs.getInt("id_fabricante"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("setor_comercial"),
                        rs.getInt("potencia"),
                        rs.getInt("amperagem"),
                        rs.getInt("codigo_sku"),
                        rs.getInt("numero_modelo"),
                        rs.getString("data_submissao"),
                        rs.getString("data_certeficacao")

                );
                equipamentos.add(equipamento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao Equipamentos: " + e.getMessage());
        }
        return equipamentos.toArray(new Equipamento[0]);
    }

    /**
     * Função para listar os equipamentos de um técnico. Recebe o id do técnico e devolve um array de equipamentos.
     * @param id_tech
     * @return array de equipamentos
     */
    public Equipamento[] listarEquipamentosInterfaceTechAccept(int id_tech){
        List<Equipamento> equipamentos = new ArrayList<>();
        String sql = "SELECT equipamentos.* from equipamentos JOIN certificacoes ON  certificacoes.id_equipamento = equipamentos.id_equipamento\n" +
                "WHERE certificacoes.id_tecnico = ? AND certificacoes.estado = 'Aceite';";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, id_tech);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Equipamento equipamento = new Equipamento(
                        rs.getInt("id_equipamento"),
                        rs.getInt("id_fabricante"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("setor_comercial"),
                        rs.getInt("potencia"),
                        rs.getInt("amperagem"),
                        rs.getInt("codigo_sku"),
                        rs.getInt("numero_modelo"),
                        rs.getString("data_submissao"),
                        rs.getString("data_certeficacao")

                );
                equipamentos.add(equipamento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao Equipamentos: " + e.getMessage());
        }
        return equipamentos.toArray(new Equipamento[0]);
    }


    /**
     * Função para contar o número de notificações por ler de um encarregado. Recebe o encarregado e devolve o número de notificações por ler.
     * @param encarregado
     * @return número de notificações por ler
     */
    public int NotificacoesPorler(String encarregado) {
        String sql = "SELECT COUNT(*) FROM notificacoes WHERE encarregado = ? AND lida = false";
        int porLer = 0;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, encarregado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                porLer = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao ler notificações: " + e.getMessage());
        }

        return porLer;
    }

    /**
     * Função para marcar todas as notificações de um encarregado como lidas. Recebe o encarregado e atualiza a tabela notificações.
     * @param encarregado
     */
    public void lerNotificacoes(String encarregado) {
        String sql = "UPDATE notificacoes SET lida = true WHERE encarregado = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, encarregado);
            stmt.executeUpdate();
            System.out.println("Notificações marcadas como lidas.");
        } catch (SQLException e) {
            System.err.println("Erro ao marcar notificações como lidas: " + e.getMessage());
        }
    }



    /**
     * Função para validar o login do utilizador através do username e password. Verifica se existe alguem na base de dados com os dados inseridos. Se existir
     * Devolve um objeto utilizador com os dados do utilizador.
     * @param username
     * @param password
     * @return
     */
    public Utilizador loginUtilizador(String username, String password) {
        String sql = "SELECT * FROM utilizadores WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            String hashedPassword = hashPassword(password);

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");

                if(estado.equals("desativo")){
                    System.out.println("\033[31mConta inativa. Por favor, contacte um gestor.\033[0m");
                    return null;
                }
                Log log = new Log(username, "Login efetuado com sucesso.");
                enviarLog(log);
                logger.info("Login efetuado com sucesso. Utilizador: " + username);
                return new Utilizador(id, nome, username, email, tipo);

            } else {
                System.out.println("\033[31mDados inválidos! Username ou senha incorretos.\033[0m");
                return null;
            }
        } catch (Exception e) {
            System.err.println("\033[31mErro ao logar utilizador: \033[0m" + e.getMessage());
            return null;
        }
    }

    /**
     * Função para ativar a conta de um utilizador quando este se regista na aplicação
     * @param user_id
     * @return true or false
     */
    public boolean ativarUtilizador(int user_id){
        String sql = "UPDATE utilizadores SET estado = 'ativo' WHERE id = ?";

        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, user_id);
            int linhasAfetadas = stmt.executeUpdate();
            Log log = new Log("Admin", "Utilizador com id: " + user_id + " ativado.");
            enviarLog(log);
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao ativar utilizador: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para desativar um utilizador.
     * @param user_id
     * @return true or false
     */
    public boolean desativarUtilizador(int user_id){
        String sql = "UPDATE utilizadores SET estado = 'desativo' WHERE id = ?";

        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, user_id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao ativar utilizador: \033[0m" + e.getMessage());
            return false;
        }
    }


    /**
     * Função para enviar um pedido de uma certificação para um equipamento, recebe um objeto certificação e insere na tabela certificações.
     * @param certeficacao
     * @return true or false
     */
    public boolean enviarCerteficacao(Certificacao certeficacao){
        String sql = "INSERT INTO certificacoes (id_equipamento, id_tecnico,id_fabricante, custo, estado) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setInt(1, certeficacao.getId_equipamento());
            stmt.setInt(2, certeficacao.getId_tecnico());
            stmt.setInt(3, certeficacao.getId_fabricante());
            stmt.setInt(4, certeficacao.getCusto());
            stmt.setString(5, certeficacao.getEstado());

            int linhasAfetadas = stmt.executeUpdate();
            String id_tecnico = Integer.toString(certeficacao.getId_tecnico());
            Log log = new Log(id_tecnico, "Pedido de certificação enviado para o equipamento com id: " + certeficacao.getId_equipamento());
            enviarLog(log);
            logger.info("Pedido de certificação enviado para o equipamento com id: " + certeficacao.getId_equipamento());
            return linhasAfetadas > 0;
        }catch(SQLException e){
            System.err.println("\033[31mErro ao enviar certeficação: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para definir o número de certificação de um equipamento. Este número é gerado automaticamente com o id da certificação e a data de realização.
     * É chamado no momento de inserir uma certificação na base de dados.
     * @return true or false
     */
    public boolean setNumero_certificacao() {
        String sql = "UPDATE certificacoes SET numero_certificacao = CAST(id_certificacao AS VARCHAR) || ? WHERE id_certificacao = (SELECT MAX(id_certificacao) FROM certificacoes)";

        LocalDate agora = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String agoraFormatado = agora.format(formatter);
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, agoraFormatado);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("\033[31mErro ao definir número de certificação: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para listar todas as certificações da base de dados. Devolve um array de certificações.
     * @return array de certificações
     */
    public Certificacao[] listarCerteficacaoInterface(){
        List<Certificacao> certificacoes = new ArrayList<>();
        String sql = "SELECT * FROM certificacoes";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Certificacao certificacao = new Certificacao(
                        rs.getInt("id_certificacao"),
                        rs.getInt("id_fabricante"),
                        rs.getInt("id_equipamento"),
                        rs.getInt("id_tecnico"),
                        rs.getString("estado"),
                        rs.getString("data_realizacao"),
                        rs.getString("numero_certificacao"),
                        rs.getString("numero_licenca"),
                        rs.getInt("custo"),
                        rs.getInt("tempo_decorrido")
                );
                certificacoes.add(certificacao);
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao listar certificações: \033[0m" + e.getMessage());
        }
        return certificacoes.toArray(new Certificacao[0]);
    }
    /**
     * Função para listar as certificações de um fabricante. Recebe o id do fabricante e devolve um array de certificações.
     * @param id_fabricante
     * @return array de certificações
     */
    public Certificacao[] listarCerteficacaoFabricanteInterface(int id_fabricante) {
        List<Certificacao> certificacoes = new ArrayList<>();
        String sql = "SELECT * FROM certificacoes WHERE id_fabricante = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id_fabricante);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Certificacao certificacao = new Certificacao(
                            rs.getInt("id_certificacao"),
                            rs.getInt("id_fabricante"),
                            rs.getInt("id_equipamento"),
                            rs.getInt("id_tecnico"),
                            rs.getString("estado"),
                            rs.getString("data_realizacao"),
                            rs.getString("numero_certificacao"),
                            rs.getString("numero_licenca"),
                            rs.getInt("custo"),
                            rs.getInt("tempo_decorrido")
                    );
                    certificacoes.add(certificacao);
                }
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao listar certificações: \033[0m" + e.getMessage());
        }
        return certificacoes.toArray(new Certificacao[0]);
    }

    /**
     * Função para listar as certificações de um técnico. Recebe o id do técnico e devolve um array de certificações.
     * @param id_tecnico
     * @return array de certificações
     */
    public Certificacao[] listarCerteficacaoTecnicoInterface(int id_tecnico) {
        List<Certificacao> certificacoes = new ArrayList<>();
        String sql = "SELECT * FROM certificacoes WHERE id_tecnico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id_tecnico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Certificacao certificacao = new Certificacao(
                            rs.getInt("id_certificacao"),
                            rs.getInt("id_fabricante"),
                            rs.getInt("id_equipamento"),
                            rs.getInt("id_tecnico"),
                            rs.getString("estado"),
                            rs.getString("data_realizacao"),
                            rs.getString("numero_certificacao"),
                            rs.getString("numero_licenca"),
                            rs.getInt("custo"),
                            rs.getInt("tempo_decorrido")
                    );
                    certificacoes.add(certificacao);
                }
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao listar certificações: \033[0m" + e.getMessage());
        }
        return certificacoes.toArray(new Certificacao[0]);
    }

    public void arquivarCerteficacao(int id_certificacao) {
        String sql = "UPDATE certificacoes SET estado = 'Arquivado' WHERE id_certificacao = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id_certificacao);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Certificação arquivada com sucesso.");
            } else {
                System.out.println("\033[31mCertificação não encontrada ou já arquivada.\033[0m");
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao arquivar certificação: \033[0m" + e.getMessage());
        }
    }

    /**
     * Função para aceitar um pedido de certificação de um equipamento. Recebe o id da certificação, o estado e o id do técnico que vai realizar a certificação.
     * Coloca o estado com aceite.
     * @param id_certificacao
     * @param estado
     * @param id_tecnico
     * @return true or false
     */
    public boolean aceitarPedidoCerteficacao(int id_certificacao, String estado, int id_tecnico){
        String sql = "UPDATE certificacoes SET estado = ?, id_tecnico = ? WHERE id_equipamento = ? OR id_certificacao = ? AND estado = 'Iniciada'";

        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, estado);
            stmt.setInt(2, id_tecnico);
            stmt.setInt(3, id_certificacao);
            stmt.setInt(4, id_certificacao);
            int linhasAfetadas = stmt.executeUpdate();
            Log log = new Log(Integer.toString(id_tecnico), "Pedido de certificação com id: " + id_certificacao + " aceite.");
            enviarLog(log);
            logger.info("Pedido de certificação com id: " + id_certificacao + " aceite.");
            return linhasAfetadas > 0;

        }catch(SQLException e){
            System.err.println("\033[31mErro ao aceitar pedido de certeficação: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para aceitar uma certificação de um equipamento. Processo final de uma certificação onde é definido o custo e alterado o estado para finalizado.
     * Por um técnino.
     * @param id_equipamento
     * @param estado
     * @param custo
     * @return true or false
     */
    public boolean aceitarCerteficacaoTecnico(int id_equipamento, String estado, double custo){
        String sql = "UPDATE certificacoes SET estado = ?, custo = ? WHERE id_equipamento = ?";

        try(PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, estado);
            stmt.setDouble(2, custo);
            stmt.setInt(3, id_equipamento);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }catch(SQLException e){
            System.err.println("\033[31mErro ao aceitar certeficação: \033[0m" + e.getMessage());
            return false;
        }
    }

    public boolean alreadyExists(String username, String email) {
        String sql = "SELECT COUNT(*) FROM utilizadores WHERE username = ? OR email = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao verificar existência de utilizador: \033[0m" + e.getMessage());
        }
        return false;
    }

    /**
     * Função para inserir um utilizador na base de dados.
     * @param utilizador
     * @return true or false
     */
    public boolean inserirUtilizador(Utilizador utilizador){
        String sql = "INSERT INTO utilizadores (nome, username, password, estado, email, tipo) VALUES (?, ?, ?, ?, ?, ?)";

        String hashedPassword = hashPassword(utilizador.getPassword());
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, utilizador.getName());
            stmt.setString(2, utilizador.getUsername());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, "ativo");
            stmt.setString(5, utilizador.getEmail());
            stmt.setString(6, utilizador.getType());

            int rowsInserted = stmt.executeUpdate();
            Log log = new Log(Integer.toString(utilizador.getId()), "Inserido com sucesso.");
            enviarLog(log);
            logger.info("Utilizador inserido com sucesso. ID: " + utilizador.getId());
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir utilizador: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para cancelar uma certificação em que é realizada uma query para buscar a data de inicio e então calcular a diferença com a data de hoje e devolver
     * em horas. Depois é realizada uma query para atualizar o estado da certificação para cancelado e o tempo decorrido.
     * @param id_certificacao
     * @param estado
     * @return true ou false
     */
    public boolean CancelarCertificacao(int id_certificacao, String estado){
        String data_inicio = "";
        String sql_data = "SELECT data_realizacao FROM certificacoes WHERE id_certificacao = ?";
        String sql_cancel = "UPDATE certificacoes SET estado = ?, tempo_decorrido = ? WHERE id_certificacao = ?";

        try(PreparedStatement stmt = conexao.prepareStatement(sql_data)){
            stmt.setInt(1, id_certificacao);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                data_inicio = rs.getString("data_realizacao");
            }else{
                return false;
            }

        }catch(SQLException e){
            System.err.println("\033[31mErro ao buscar data: \033[0m" + e.getMessage());
            return false;
        }

        DateTimeFormatter formatar = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dataOriginal = LocalDateTime.parse(data_inicio, formatar);

        LocalDateTime agora = LocalDateTime.now();

        long diferencahoras = Duration.between(dataOriginal, agora).toHours();

        try(PreparedStatement stmt = conexao.prepareStatement(sql_cancel)){
            stmt.setString(1, estado);
            stmt.setInt(2, (int)diferencahoras);
            stmt.setInt(3, id_certificacao);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        }catch(SQLException e){
            System.err.println("\033[31mErro ao cancelar certificacao: \033[0m" + e.getMessage());
            return false;
        }
    }

    /**
     * Função para inserir um fabricante na base de dados.
     * @param fabricante
     * @return true or false
     */
    public boolean inserirFabricante(Fabricante fabricante){
        String sql = "INSERT INTO utilizadores (nome, username, password, email, tipo, nif, telefone, morada, sector_comercial, data_inicio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        String hashedPassword = hashPassword(fabricante.getPassword());

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, fabricante.getName());
            stmt.setString(2, fabricante.getUsername());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, fabricante.getEmail());
            stmt.setString(5, fabricante.getType());
            stmt.setString(6, fabricante.getNif());
            stmt.setString(7, fabricante.getTelefone());
            stmt.setString(8, fabricante.getMorada());
            stmt.setString(9, fabricante.getSector_comercial());
            stmt.setDate(10, java.sql.Date.valueOf(fabricante.getData()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id");
                fabricante.setId(idGerado);
                enviarNotificacao(idGerado, "Pedido de Registo de Conta", "fabricante", "Gestores");
                Log log = new Log(Integer.toString(idGerado), "Fabricante inserido com sucesso.");
                enviarLog(log);
                logger.info("Fabricante inserido com sucesso. ID: " + idGerado);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir fabricante: \033[0m" + e.getMessage());
        }
        return false;
    }

    /**
     * Função para inserir um técnico na base de dados.
     * @param tecnico
     * @return true or false
     */
    public boolean inserirTecnico(Tecnico tecnico){
        String sql = "INSERT INTO utilizadores (nome, username, password, email, tipo, nif, telefone, morada, area_especializacao, nivel_certificacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String hashedPassword = hashPassword(tecnico.getPassword());
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, tecnico.getName());
            stmt.setString(2, tecnico.getUsername());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, tecnico.getEmail());
            stmt.setString(5, tecnico.getType());
            stmt.setString(6, tecnico.getNif());
            stmt.setString(7, tecnico.getTelefone());
            stmt.setString(8, tecnico.getMorada());
            stmt.setString(9, tecnico.getArea_especializacao());
            stmt.setInt(10, tecnico.getNivel_certificacao());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idGerado = rs.getInt("id");
                tecnico.setId(idGerado);
                enviarNotificacao(idGerado, "Pedido de Registo de Conta", "tecnico", "Gestores");
                Log log = new Log(Integer.toString(idGerado), "Técnico inserido com sucesso.");
                enviarLog(log);
                logger.info("Técnico inserido com sucesso. ID: " + idGerado);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("\033[31mErro ao inserir técnico: \033[0m" + e.getMessage());
        }
        return false;
    }

}