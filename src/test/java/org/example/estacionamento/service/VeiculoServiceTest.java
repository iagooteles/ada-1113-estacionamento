package org.example.estacionamento.service;

import org.example.estacionamento.model.Veiculo;
import org.example.estacionamento.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService service;

    @Mock
    private VeiculoRepository repository;

    @Test
    public void nao_deve_permitir_cadastro_de_um_veiculo_ja_existente() {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo("X");
        veiculo.setPlaca("Y");

        when(repository.existsById(any())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.novoVeiculo(veiculo));

        assertEquals("Veiculo ja existente", exception.getMessage());

        verify(repository).existsById(veiculo.getPlaca());
        verify(repository, never()).save(any());
    }

    @Test
    public void nao_deve_permitir_alterar_veiculo_nao_existente() {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo("Z");
        veiculo.setPlaca("ZZZ-1234");

        when(repository.existsById(veiculo.getPlaca())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.alterarVeiculo(veiculo));

        assertEquals("Veiculo não existente", exception.getMessage());

        verify(repository).existsById(veiculo.getPlaca());
        verify(repository, never()).save(any());
    }
}
