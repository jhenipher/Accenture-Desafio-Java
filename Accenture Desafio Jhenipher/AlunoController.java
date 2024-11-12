@RestController
@RequestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private InscricaoRepository inscricaoRepository;
    @Autowired
    private InscricaoPopulator populator;

    @PostMapping
    public Aluno cadastrarAluno(@RequestBody Aluno aluno) {
        aluno.setDataCadastro(LocalDate.now());
        return alunoRepository.save(aluno);
    }

    @PostMapping("/{alunoId}/inscrever/{cursoId}")
    public ResponseEntity<?> inscreverAluno(@PathVariable Long alunoId, @PathVariable Long cursoId) {
        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        Curso curso = cursoRepository.findById(cursoId).orElse(null);

        if (aluno == null || curso == null) {
            return ResponseEntity.badRequest().body("Aluno ou Curso não encontrado");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setAluno(aluno);
        inscricao.setCurso(curso);
        inscricao.setDataInscricao(LocalDate.now());
        inscricaoRepository.save(inscricao);

        return ResponseEntity.ok("Aluno inscrito com sucesso");
    }

    @GetMapping("/{alunoId}/cursos")
    public List<CursoDTO> listarCursosAluno(@PathVariable Long alunoId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoId(alunoId);
        return inscricoes.stream().map(i -> populator.toCursoDTO(i.getCurso())).collect(Collectors.toList());
    }
}
