package interpreter

/*
 * VEUILLEZ INSCRIRE CI-DESSOUS VOTRE NOM ET VOTRE PRENOM :
 *
 * ETUDIANT 1 : AUVRAY Naomi
 *
 * ETUDIANT 2 : LEBAUD Antoine
 *
 */

/* Les exceptions suivantes sont définies pour le cas où vous voudriez garantir
 *  - que des listes de variables ne sont pas vides
 *  - que des listes de commandes ne sont pas vides
 *  - que des listes de variables et de valeurs sont de même longueur
 *
 * Déclencher ces exceptions se fait par  :
 *   - throw ExceptionListeVide
 *   - throw ExceptionListesDeLongueursDifferentes
 */

/** définition d'une exception pour le cas des listes vides
 */
case object ExceptionListeVide extends Exception

/** définition d'une exception pour le cas des listes de tailles différentes
 */
case object ExceptionListesDeLongueursDifferentes extends Exception


object Interpreter {

  /** UN INTERPRETER POUR LE LANGAGE WHILE
   */

  /** GESTION DE LA MEMOIRE DE L'INTERPRETEUR
   */

  /** définition d'un type Memory pour représenter une mémoire
   */
  type Memory = Map[Variable, Value]

  /** @param v
   *   : une variable
   * @param mem
   *   : une mémoire
   * @return
   *   m(v), c'est-à-dire la valeur de la variable v dans la mémoire mem, la
   *   valeur par défaut si la variable v n'est pas présente dans la mémoire
   *   mem
   */
  def lookUp(v: Variable, mem: Memory): Value =
    mem.getOrElse(v,NlValue)

  /** @param v
   *   : une variable
   * @param d
   *   : une valeur
   * @param mem
   *   : une mémoire
   * @return
   *   la mémoire modifiée par l'affectation [v->d]
   */

  def assign(v: Variable, d: Value, mem: Memory): Memory =
    mem.updated(v,d)


  /** TRAITEMENT DES EXPRESSIONS DU LANGAGE WHILE
   */

  /** @param expression
   *   : un AST décrivant une expression du langage WHILE
   * @return
   *   la valeur de l'expression
   */
  // TODO PROJET2
  def interpreterExpr(expression: Expression, mem: Memory): Value = ???

  /** La fonction interpreterExpr ci-dessus calcule la valeur associée à une
   * expression ; il peut être utile de produire à l'inverse une expression
   * associée à une valeur. La fonction valueToExpression ci-dessous construira
   * l'expression la plus simple associée à une valeur
   *
   * @param value
   *   : une valeur du langage WHILE
   * @return
   *   l'AST décrivant l'expression de cette valeur
   */
  // TODO PROJET2
  def valueToExpression(value: Value): Expression = ???


  /** TRAITEMENT DES COMMANDES DU LANGAGE WHILE
   */

  /** @param command
   *   : un AST décrivant une commande du langage WHILE
   * @param memory
   *   : une mémoire
   * @return
   *   la mémoire après l'interprétation de la commande
   */
  // TODO PROJET2
  def interpreterCommand(command: Command, memory: Memory): Memory =
    command match {
      case Nop => memory
      case Set(variable : Variable, valeur :Value) => assign(variable, valeur, memory)
      case While(exp : Expression, command : Command) => ???
      case For (count : Int, exp : Expression) => 
        count match {
          case 0 => ???
          case _ =>count --
        } 
      case If => ???
    }


  /** @param commands
   *   : une liste non vide d'AST décrivant une liste non vide de commandes du
   *   langage WHILE
   * @param memory
   *   : une mémoire
   * @return
   *   la mémoire après l'interprétation de la liste de commandes
   */
  // TODO PROJET2
  def interpreterCommands(commands: List[Command], memory: Memory): Memory = ???
    


  /** TRAITEMENT DES PROGRAMMES DU LANGAGE WHILE
   */

  /** @param vars
   *   : une liste non vide décrivant les variables d'entrée d'un programme du
   *   langage WHILE
   * @param vals
   *   : une liste non vide de valeurs
   * @return
   *   une mémoire associant chaque valeur à la variable d'entrée correspondant
   */
  // TODO PROJET2
  def interpreterMemorySet(vars: List[Variable], vals: List[Value]): Memory = ???


  /** @param vars
   *   : une liste non vide décrivant les variables de sortie d'un programme du
   *   langage WHILE
   * @param memory
   *   : une mémoire
   * @return
   *   la liste des valeurs des variables de sortie
   */
  // TODO PROJET2
  def interpreterMemoryGet(vars: List[Variable], memory: Memory): List[Value] = ???


  /** @param program
   *   : un AST décrivant un programme du langage WHILE
   * @param vals
   *   : une liste de valeurs
   * @return
   *   la liste des valeurs des variables de sortie
   */
  // TODO PROJET2
  def interpreter(program: Program, vals: List[Value]): List[Value] = ???

}