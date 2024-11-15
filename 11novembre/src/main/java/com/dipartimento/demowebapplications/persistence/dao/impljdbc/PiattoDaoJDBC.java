package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDaoJDBC implements PiattoDao {

    Connection connection;


    public PiattoDaoJDBC(Connection conn){
        this.connection = conn;
    }

    @Override
    public List<Piatto> findAll() {
        List<Piatto> piatti = new ArrayList<Piatto>();
        String query = "select * from piatto";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Piatto piatto = new PiattoProxy();

                piatto.setNome(rs.getString("nome"));
                piatto.setIngredienti(rs.getString("ingredienti"));

                piatti.add(piatto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return piatti;
    }

    @Override
    public Piatto findByPrimaryKey(String nome) {
        String query = "SELECT nome, ingredienti FROM piatto WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new PiattoProxy(resultSet.getString("nome"), resultSet.getString("ingredienti"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void save(Piatto piatto) {


        String query = "INSERT INTO piatto (nome, ingredienti) VALUES (?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET ingredienti = EXCLUDED.ingredienti";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piatto.getNome());
            statement.setString(2, piatto.getIngredienti());
            statement.executeUpdate();
            List<Ristorante> ristoranti = piatto.getRistoranti();
            if(ristoranti==null|| ristoranti.isEmpty()){
                return;
            }
            restRelationsPResentInTheJoinTable(connection,piatto.getNome());
            RistoranteDao rd=DBManager.getInstance().getRistoranteDao();
            for( Ristorante r:ristoranti){
                rd.save(r);
                insertJoinRistorantePiatto(connection,r.getNome(),piatto.getNome());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void restRelationsPResentInTheJoinTable(Connection connection, String nomePiatto) throws Exception {

        String query="Delete FROM ristorante_piatto WHERE piatto_nome= ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomePiatto);


        preparedStatement.execute();

    }

    private void insertJoinRistorantePiatto(Connection connection , String nomeRistorante, String nomePiatto) throws SQLException {

        String query="INSERT INTO ristorante_piatto (ristorante_nome,piatto_nome) VALUES (? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);
        preparedStatement.setString(2, nomePiatto);

        preparedStatement.execute();

    }

    @Override
    public void delete(Piatto piatto) {
        String query="Delete FROM piatto WHERE nome= ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,piatto.getNome() );
            preparedStatement.execute();
            this.restRelationsPResentInTheJoinTable(connection , piatto.getNome());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Piatto> findAllByRistoranteName(String ristoranteNome) {



        List<Piatto> piatti = new ArrayList<>();
        String query = "SELECT p.nome, p.ingredienti FROM piatto p " +
                "JOIN ristorante_piatto rp ON p.nome = rp.piatto_nome " +
                "WHERE rp.ristorante_nome = ?";

        System.out.println("going to execute:"+query);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristoranteNome);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String ingredienti = resultSet.getString("ingredienti");
                piatti.add(new PiattoProxy(nome, ingredienti));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return piatti;
    }

    @Override
    public void update(Piatto piatto, String ingredienti) {
        String query = "UPDATE piatto SET ingredienti = ? WHERE nome = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,ingredienti );
            preparedStatement.setString(2,piatto.getNome() );
            preparedStatement.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
        List<Piatto> piatti = piattoDao.findAll();
        for (Piatto piatto : piatti) {
            System.out.println(piatto.getNome());
            System.out.println(piatto.getIngredienti());

        }
    }
}
