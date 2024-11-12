public class InscricaoPopulator {
    public AlunoDTO toAlunoDTO(Aluno aluno) {
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }

    public CursoDTO toCursoDTO(Curso curso) {
        return new CursoDTO(curso.getId(), curso.getNome(), curso.getDescricao());
    }
}
