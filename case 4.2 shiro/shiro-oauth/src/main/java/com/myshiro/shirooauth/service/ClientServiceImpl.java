package com.myshiro.shirooauth.service;

import com.myshiro.shirooauth.entity.Client;
import com.myshiro.shirooauth.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/26 22:43
 * @Version 1.0
 **/
@Transactional
@Service
public class ClientServiceImpl implements ClientService  {

    @Autowired
    private ClientMapper clientDao;

    @Override
    public Client createClient(Client client) {

        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        return clientDao.createClient(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientDao.updateClient(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientDao.deleteClient(id);
    }

    @Override
    public Client findOne(Long id) {
        return clientDao.findOne(id);
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public Client findByClientId(String clientId) {
        return clientDao.findByClientId(clientId);
    }

    @Override
    public Client findByClientSecret(String clientSecret) {
        return clientDao.findByClientSecret(clientSecret);
    }
}
