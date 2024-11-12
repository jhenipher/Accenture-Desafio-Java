@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private InscricaoRepository inscricaoRepository;
    @Autowired
    private InscricaoPopulator populator;

    @PostMapping
    public Curso cadastrarCurso(@RequestBody Curso curso) {
        curso.setDataCriacao(LocalDate.now());
        return cursoRepository.save(curso);
    }

    @GetMapping("/{cursoId}/alunos")
    public List<AlunoDTO> listarAlunosCurso(@PathVariable Long cursoId) {
        List<Inscricao> inscricoes = inscricaoRepository.findByCursoId(cursoId);
        return inscricoes.stream().map(i -> populator.toAlunoDTO(i.getAluno())).collect(Collectors.toList());
    }
}
