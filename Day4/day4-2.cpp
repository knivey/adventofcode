#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <regex>
#include <map>

class passport {
    std::map<std::string, std::string> fields;
    
    public:
    passport(std::vector<std::string> parts) {
        try {
            const std::regex byr(R"((byr):(\d{4}))");                      //(Birth Year) - four digits; at least 1920 and at most 2002.
            const std::regex iyr(R"((iyr):(\d{4}))");                      //(Issue Year) - four digits; at least 2010 and at most 2020.
            const std::regex eyr(R"((eyr):(\d{4}))");                      //(Expiration Year) - four digits; at least 2020 and at most 2030.
            const std::regex hgt(R"((hgt):((\d?\d\d)(cm|in)))");           //(Height) - a number followed by either cm or in:
                                                                             // If cm, the number must be at least 150 and at most 193.
                                                                             // If in, the number must be at least 59 and at most 76.
            const std::regex hcl(R"((hcl):(\#[0-9a-f]{6}))");              //(Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            const std::regex ecl(R"((ecl):(amb|blu|brn|gry|grn|hzl|oth))"); //(Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            const std::regex pid(R"((pid):(\d{9}))");                      //(Passport ID) - a nine-digit number, including leading zeroes.
                                                                           //(Country ID) - ignored, missing or not.

            for (auto p : parts) {
                std::smatch match;
                if(std::regex_match(p, match, byr)) {
                    if((std::stoi(match[2]) < 1920 || std::stoi(match[2]) > 2002))
                        continue;
                    goto add;
                }
                if(std::regex_match(p, match, iyr)) {
                    if(std::stoi(match[2]) < 2010 || std::stoi(match[2]) > 2020)
                        continue;
                    goto add;
                }
                if(std::regex_match(p, match, eyr)) {
                    if(std::stoi(match[2]) < 2020 || std::stoi(match[2]) > 2030)
                        continue;
                    goto add;
                }
                if(std::regex_match(p, match, hgt)) {
                    if(match[4] == "cm" && (std::stoi(match[3]) < 150 || std::stoi(match[3]) > 193))
                        continue;
                    if(match[4] == "in" && (std::stoi(match[3]) < 59 || std::stoi(match[3]) > 76))
                        continue;
                    goto add;
                }
                if(std::regex_match(p, match, hcl) || std::regex_match(p, match, ecl) || std::regex_match(p, match, pid)) {
                    goto add;
                }
                continue;

                add:
                fields[match[1]] = match[2];
                //std::cout << match[1] << "=" << match[2] << ", ";
                
            }
            //std::cout << std::endl;
        } catch (std::regex_error& e) {
            std::cerr << "Regex error: " << e.code() << std::endl;
        }
    }
    
    bool valid() {
        for(auto e : {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}) {
            if(fields[e] == "") {
                //std::cout << "  Missing: " << e << " " << fields[e] << std::endl;
                return false;
            }
        }
        return true;
    }
};

int main() {
    //File should have trailnig newline!
    std::ifstream input("day4_input.txt");
    std::string line;
    uint valid = 0;
    passport *p = nullptr;
    std::vector<std::string> parts;
    while (std::getline(input, line))
    {
        if(line.empty()) {
            if(p == nullptr)
                delete p;
            p = new passport(parts);
            if(p->valid())
                valid++;
            parts.clear();
            continue;
        }
        std::istringstream iss(line);        
        std::string part;
        while ((iss >> part)) { 
            parts.emplace_back(part);
        }
    }
    std::cout << "Valid: " << valid << std::endl;
    if(p == nullptr)
        delete p;
    return 0;
}
