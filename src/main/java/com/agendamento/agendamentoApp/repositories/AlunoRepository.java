package com.agendamento.agendamentoApp.repositories;

import com.agendamento.agendamentoApp.models.Aluno;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AlunoRepository implements MongoRepository<Aluno, String> {
    @Override
    public <S extends Aluno> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Aluno> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Aluno> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Aluno> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Aluno> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Aluno> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Aluno> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Aluno> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Aluno, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Aluno> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Aluno> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Aluno> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Aluno> findAll() {
        return null;
    }

    @Override
    public List<Aluno> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Aluno entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Aluno> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Aluno> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Aluno> findAll(Pageable pageable) {
        return null;
    }
}
