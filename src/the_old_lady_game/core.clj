(ns the-old-lady-game.core
  (require  [clojure.pprint :as pprint]
            [clojure.string :as s])
  (:gen-class))

(def a-nought " O ")

(def a-cross " X ")

(defn board [size]
  (let [board-rows (partition size (mapv #(str "[" % "]") (range (* size size))))]
    (mapv #(into [] %) board-rows)))

(defn visual-board [board]
  "Visual representation on the board for terminal"
    (str "\n" (s/join "\n\n" (map #(s/join "\t" %) board)) "\n\n"))

(defn position->dimensions [board position]
  "Returns the dimension tuple [row, column] based on position index"
  (let [width (Math/sqrt (count (flatten board)))
        row (int (Math/floor (/ position width)))
        column (int (mod position width))]
      (vector row column)))

(defn valid-move? [board tuple]
  "Checks if the move is valid, that means, there's not any symbol
  on the cell yet"
  (not (or (= a-nought (get-in board tuple)) (= a-cross (get-in board tuple)))))

(defn symbol-count [col symbol]
  "Counts the number of occurrences of a symbol in a collection"
  (count (filter #(= symbol %) col)))

(defn check-row [row]
  "Checks if the row contains only one character type. That means this is a winning row"
  (if (= 1 (count (frequencies row))) (first row)))

(defn transpose [matrix]
  "Transposes the board matrix"
  (apply mapv vector matrix))

(defn get-diagonal [board]
  "Gets diagonal values of a n-sized square matrix (noughts-and-crosses board)"
  (into [] (map #(get-in board %) (map #(vector % %) (range (count board))))))

(defn check-diagonals [board]
  "Checks the two board diagonals and return winner symbol
  if either one is a winning row"
  (or (check-row (get-diagonal board))
      (check-row (get-diagonal (into [] (reverse board))))))

(defn check-winner [board]
  "Returns the winner of a board or 'nil' if no winner"
  (or (check-diagonals board) (some identity (map #(check-row %) board))
    (some identity (map #(check-row %) (transpose board)))))

(defn sanitise [position board]
  "Sanitise position to protect against invalid entries raising exceptions"
  (try
    (let [num (Integer. position)]
      (if (and (not (neg? num)) (< num (* (count board) (count board)))) position -1))
   (catch Exception e -1)))

(defn play [board position]
  "Performs the move based on the current board and position provided.
  Returns the updated board or nil in case it's a game over (by win or draw)"
  (let [sanitised-position (sanitise position board)
        tuple (position->dimensions board (Integer. sanitised-position))
        flat-board (flatten board)
        noughts-count (symbol-count flat-board a-nought)
        crosses-count (symbol-count flat-board a-cross)
        player-symbol (if (> crosses-count noughts-count) a-nought a-cross)]
        (if (and (not (= -1 sanitised-position)) (valid-move? board tuple))
          (let [updated-board (assoc-in board tuple player-symbol)
                winner (check-winner updated-board)]
            (do
              (println "Current board: \n " (visual-board updated-board))
              (if winner (println "Winner:" winner))
              (if (not winner) updated-board)))
          (do
            (println "Invalid move, try again. Current board: \n " (visual-board board))
            board))))

(defn valid-dimension? [arg]
  (try
    (let [num (Integer. arg)]
      (and (pos? num) (< num 100)))
   (catch Exception e false)))

(defn -main
  [& args]
    (if (valid-dimension? (first args))
      (let [dimension (Integer. (first args))
            gameboard (board dimension)]
        (println "\n===== NEW GAME STARTED! =====")
        (println (visual-board gameboard))
        (println "Player 'X' starts. Choose a position by entering its number"
                 " (or \"quit\" to quit the game)")
        (loop [board gameboard]
          (when-not (or (nil? board))
          (let [input (read-line)]
            (if (not (= "quit" input))
              (recur (play board input)))))))
      (println "Invalid entry. Please provide valid board dimension"
                "(integer between 1 and 100)."))
    (println ">>> GAME OVER <<<"))
