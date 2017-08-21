(ns the-old-lady-game.core-test
  (:require [clojure.test :refer :all]
            [the-old-lady-game.core :refer :all]))

(deftest a-test
  (testing "generate board size 0"
    (is (= [] (board 0))))

    (testing "generate board size 1"
      (is (= [["[0]"]] (board 1))))

    (testing "generate board size 3"
      (let [expected-board [["[0]" "[1]" "[2]"] ["[3]" "[4]" "[5]"]
                            ["[6]" "[7]" "[8]"]]]
      (is (= expected-board (board 3)))))

    (testing "generate board size 4"
      (let [expected-board [["[0]" "[1]" "[2]" "[3]"]
                            ["[4]" "[5]" "[6]" "[7]"]
                            ["[8]" "[9]" "[10]" "[11]"]
                            ["[12]" "[13]" "[14]" "[15]"]]]
      (is (= expected-board (board 4)))))

    (testing "dimensions from position 2 on 3x3 board"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]
            tuple (position->dimensions board 2)]
          (is (= [0 2] tuple))))

    (testing "dimensions from position 6 on 3x3 board"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]
            tuple (position->dimensions board 6)]
          (is (= [2 0] tuple))))

    (testing "valid move"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" a-cross "[5]"]
                 ["[6]" "[7]" "[8]"]]]
          (is (valid-move? board [0 1]))))

    (testing "invalid move"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" a-cross "[5]"]
                 ["[6]" "[7]" "[8]"]]]
          (is (not (valid-move? board [1 1])))))

    (testing "existing symbol count"
      (is (= 3 (symbol-count [1 1 1 2 3 4 5] 1))))

    (testing "non-existing symbol count"
      (is (= 0 (symbol-count [1 1 1 2 3 4 5] 9))))

    (testing "check winner row"
      (is (check-row [a-cross a-cross a-cross])))

    (testing "check non-winner row"
      (is (not (check-row [a-cross a-nought "2"]))))

    (testing "get diagonal"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]]
          (is (= (get-diagonal board) ["[0]" "[4]" "[8]"]))))

    (testing "check winner in row"
      (let [board [[a-cross "[1]" a-cross]
                   [a-nought a-nought a-nought]
                   ["[6]" a-cross a-cross]]]
          (is (= (check-winner board) a-nought))))

    (testing "check winner in column"
      (let [board [[a-cross "[1]" a-cross]
                   [a-nought a-nought a-cross]
                   ["[6]" a-nought a-cross]]]
          (is (= (check-winner board) a-cross))))

    (testing "check winner in second diagonal"
      (let [board [[a-cross "[1]" a-cross]
                   [a-nought a-cross a-nought]
                   [a-cross a-nought "[8]"]]]
          (is (= (check-winner board) a-cross))))

    (testing "sanitise negative"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]]
        (is (= -1 (sanitise -6 board)))))

    (testing "sanitise too large"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]]
        (is (= -1 (sanitise 100000 board)))))

    (testing "sanitise weird chars"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]]
        (is (= -1 (sanitise "iGGar!" board)))))

    (testing "sanitise zero"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]]
        (is (= 0 (sanitise 0 board)))))

    (testing "sanitise position 4 on a 3x3 board"
      (let [board [["[0]" "[1]" "[2]"]
                 ["[3]" "[4]" "[5]"]
                 ["[6]" "[7]" "[8]"]]]
        (is (= 4 (sanitise 4 board))))))
