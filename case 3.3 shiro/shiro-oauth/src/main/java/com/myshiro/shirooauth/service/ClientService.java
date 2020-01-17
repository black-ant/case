package com.myshiro.shirooauth.service;

import com.myshiro.shirooauth.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/25 22:18
 * @Version 1.0
 **/
@Service
public interface ClientService {
    public Client createClient(Client client);
    public Client updateClient(Client client);
    public void deleteClient(Long clientId);

    Client findOne(Long clientId);

    List<Client> findAll();

    Client findByClientId(String clientId);
    Client findByClientSecret(String clientSecret);
}
